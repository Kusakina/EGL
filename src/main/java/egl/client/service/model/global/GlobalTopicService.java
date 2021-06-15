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

    private final Map<Long, Optional<Topic>> localToGlobalCache;

    public GlobalTopicService(
            GlobalTopicRepository repository,
            LocalTopicInfoService localTopicInfoService,
            GlobalTopicInfoService globalTopicInfoService) {
        super(repository);
        this.localTopicInfoService = localTopicInfoService;
        this.globalTopicInfoService = globalTopicInfoService;
        this.localToGlobalCache = new HashMap<>();
    }

    private Optional<Topic> remoteFindBy(long globalId) {
        try {
            return repository.findById(globalId);
        } catch (RuntimeException e) {
            throw new EntityServiceException();
        }
//            .filter(globalTopic -> {
//                var globalTopicInfo = globalTopicInfoService.findBy(globalTopic);
//                return globalTopicInfo.getLocalHashCode() == localTopicInfo.getGlobalHashCode();
//            });
    }

    @Override
    public Optional<Long> findGlobalIdByLocal(Topic localTopic) {
        var globalId = localTopicInfoService.findBy(localTopic).getGlobalId();
        return (LocalTopicInfo.NO_GLOBAL_ID == globalId) ? Optional.empty() : Optional.of(globalId);
    }

    @Override
    public Optional<Topic> findTopicByLocal(Topic localTopic) {
        return findGlobalIdByLocal(localTopic)
                .flatMap(globalId -> localToGlobalCache
                        .computeIfAbsent(globalId, this::remoteFindBy)
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
