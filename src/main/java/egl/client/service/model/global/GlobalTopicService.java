package egl.client.service.model.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicInfo;
import egl.client.model.global.topic.GlobalTopicInfo;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.repository.global.topic.GlobalTopicRepository;
import egl.client.service.model.core.TopicService;
import egl.client.service.model.local.LocalTopicInfoService;
import org.springframework.stereotype.Service;

@Service
public class GlobalTopicService
        extends TopicService<GlobalTopicRepository> {

    private final GlobalTopicInfoService globalTopicInfoService;

    private final Map<Long, Topic> hashToGlobalCache;

    public GlobalTopicService(
            GlobalTopicRepository repository,
            LocalTopicInfoService localTopicInfoService,
            GlobalTopicInfoService globalTopicInfoService) {
        super(repository, localTopicInfoService);
        this.globalTopicInfoService = globalTopicInfoService;
        this.hashToGlobalCache = new HashMap<>();
    }

    @Override
    public Optional<Topic> findTopicByLocal(Topic localTopic) {
        var hash = findHashByLocal(localTopic);
        return Optional.ofNullable(
                hashToGlobalCache.getOrDefault(hash, null)
        );
    }

    public void initializeRegistrations(List<Topic> localTopics) {
        localTopics.forEach(this::initializeRegistration);
    }

    public void initializeRegistration(Topic localTopic) {
        var localTopicInfo = localTopicInfoService.findBy(localTopic);
        initializeRegistration(localTopicInfo);
    }

    public void initializeRegistration(TopicInfo localTopicInfo) {
        registerTopic(localTopicInfo, () -> null);
    }

    public void registerTopic(LocalTopicInfo localTopicInfo) {
        registerTopic(localTopicInfo, () -> {
            var globalTopicInfo = globalTopicInfoService.save(
                    localTopicInfo.getTopic(), localTopicInfo.getTopicHashCode()
            );
            return globalTopicInfo.getTopic();
        });
    }

    private void registerTopic(TopicInfo localTopicInfo, Supplier<Topic> missedTopicSupplier) {
        hashToGlobalCache.computeIfAbsent(
                localTopicInfo.getTopicHashCode(),
                hashCode -> globalTopicInfoService.findBy(hashCode)
                        .map(GlobalTopicInfo::getTopic)
                        .orElseGet(missedTopicSupplier)
        );
    }
}
