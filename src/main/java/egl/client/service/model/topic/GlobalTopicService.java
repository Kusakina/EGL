package egl.client.service.model.topic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import egl.client.model.core.topic.Topic;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.repository.global.topic.GlobalTopicRepository;
import egl.client.service.model.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class GlobalTopicService extends AbstractEntityService<Topic, GlobalTopicRepository> {

    private final LocalTopicInfoService localTopicInfoService;

    private final Map<Topic, Optional<Topic>> localToGlobalCache;

    public GlobalTopicService(
            GlobalTopicRepository repository,
            LocalTopicInfoService localTopicInfoService) {
        super(repository);
        this.localTopicInfoService = localTopicInfoService;
        this.localToGlobalCache = new HashMap<>();
    }

    private Optional<Topic> remoteFindByLocal(Topic localTopic) {
        var localTopicInfo = localTopicInfoService.findBy(localTopic);

        var globalTopicId = localTopicInfo.getGlobalId();
        if (LocalTopicInfo.NO_GLOBAL_ID == globalTopicId) {
            return Optional.empty();
        } else {
            return repository.findById(globalTopicId);
        }
    }

    public Optional<Topic> findByLocal(Topic localTopic) {
        return localToGlobalCache.computeIfAbsent(
                localTopic, this::remoteFindByLocal
        );
    }
}
