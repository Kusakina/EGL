package egl.client.service.model.global;

import egl.client.model.core.topic.Topic;
import egl.client.model.global.topic.GlobalTopicInfo;
import egl.client.repository.global.topic.GlobalTopicInfoRepository;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class GlobalTopicInfoService extends AbstractEntityService<GlobalTopicInfo, GlobalTopicInfoRepository> {

    public GlobalTopicInfoService(GlobalTopicInfoRepository repository) {
        super(repository);
    }

    public GlobalTopicInfo findBy(Topic topic) {
        try {
            return repository.findByTopic(topic);
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }
}
