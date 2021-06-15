package egl.client.service.model.global;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.topic.Topic;
import egl.client.model.global.topic.GlobalTopicInfo;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.repository.global.topic.GlobalTopicRepository;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.AbstractEntityService;
import egl.client.service.model.core.TopicByLocalService;
import egl.client.service.model.local.LocalTopicInfoService;
import org.springframework.stereotype.Service;

@Service
public class GlobalTopicService
        extends AbstractEntityService<Topic, GlobalTopicRepository>
        implements TopicByLocalService  {

    private final LocalTopicInfoService localTopicInfoService;
    private final GlobalTopicInfoService globalTopicInfoService;

    private final Map<LocalTopicInfo, Optional<Topic>> localToGlobalCache;

    public GlobalTopicService(
            GlobalTopicRepository repository,
            LocalTopicInfoService localTopicInfoService,
            GlobalTopicInfoService globalTopicInfoService) {
        super(repository);
        this.localTopicInfoService = localTopicInfoService;
        this.globalTopicInfoService = globalTopicInfoService;
        this.localToGlobalCache = new HashMap<>();
    }

    private Optional<Topic> remoteFindByLocal(LocalTopicInfo localTopicInfo) {
        try {
            return repository.findById(localTopicInfo.getGlobalId());
        } catch (RuntimeException e) {
            throw new EntityServiceException();
        }
//            .filter(globalTopic -> {
//                var globalTopicInfo = globalTopicInfoService.findBy(globalTopic);
//                return globalTopicInfo.getLocalHashCode() == localTopicInfo.getGlobalHashCode();
//            });
    }

    @Override
    public Optional<Topic> findTopicByLocal(Topic localTopic) {
        var localTopicInfo = localTopicInfoService.findBy(localTopic);
        if (null == localTopicInfo) return Optional.empty();

        var globalTopicId = localTopicInfo.getGlobalId();
        if (LocalTopicInfo.NO_GLOBAL_ID == globalTopicId) {
            return Optional.empty();
        }

        return localToGlobalCache.computeIfAbsent(
                localTopicInfo, this::remoteFindByLocal
        );
    }

    public void registerTopic(LocalTopicInfo localTopicInfo, Profile author) {
        var globalTopicInfo = new GlobalTopicInfo(
                localTopicInfo.getTopic(), localTopicInfo.getGlobalHashCode(), author
        );

        var globalTopic = globalTopicInfoService.save(globalTopicInfo).getTopic();
        localTopicInfo.setGlobalId(globalTopic.getId());
        localTopicInfoService.save(localTopicInfo);
    }
}
