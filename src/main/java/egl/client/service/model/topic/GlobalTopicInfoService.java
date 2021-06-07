package egl.client.service.model.topic;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.topic.Topic;
import egl.client.model.global.topic.GlobalTopicInfo;
import egl.client.repository.global.topic.GlobalTopicInfoRepository;
import egl.client.service.model.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class GlobalTopicInfoService extends AbstractEntityService<GlobalTopicInfo, GlobalTopicInfoRepository> {

    public GlobalTopicInfoService(GlobalTopicInfoRepository repository) {
        super(repository);
    }

    public GlobalTopicInfo findBy(Topic topic, int localHashCode) {
        return repository.findByTopicAndLocalHashCode(topic, localHashCode);
    }

    public GlobalTopicInfo save(Topic localTopic, int localHashCode, Profile author) {
        var globalTopicInfo = new GlobalTopicInfo(
                localTopic, localHashCode, author
        );

        return save(globalTopicInfo);
    }
}
