package egl.client.service.model.global;

import egl.client.model.core.topic.Topic;
import egl.client.model.global.topic.GlobalTopicInfo;
import egl.client.repository.global.topic.GlobalTopicInfoRepository;
import egl.client.service.model.core.TopicInfoService;
import org.springframework.stereotype.Service;

@Service
public class GlobalTopicInfoService
        extends TopicInfoService<GlobalTopicInfo, GlobalTopicInfoRepository> {

    public GlobalTopicInfoService(GlobalTopicInfoRepository repository) {
        super(repository);
    }

    public GlobalTopicInfo save(Topic topic, long hashCode) {
        var globalTopicInfo = new GlobalTopicInfo(topic, hashCode);
        return save(globalTopicInfo);
    }
}
