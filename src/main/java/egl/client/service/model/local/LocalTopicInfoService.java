package egl.client.service.model.local;

import egl.client.model.core.topic.Topic;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.repository.local.topic.LocalTopicInfoRepository;
import egl.client.service.model.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class LocalTopicInfoService extends AbstractEntityService<LocalTopicInfo, LocalTopicInfoRepository> {

    public LocalTopicInfoService(LocalTopicInfoRepository repository) {
        super(repository);
    }

    public LocalTopicInfo findBy(Topic topic) {
        return repository.findByTopic(topic);
    }
}
