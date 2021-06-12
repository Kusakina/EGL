package egl.client.service.model.local;

import java.util.List;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.local.topic.LocalTopicRepository;
import egl.client.service.model.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class LocalTopicService extends AbstractEntityService<Topic, LocalTopicRepository> {

    public LocalTopicService(LocalTopicRepository repository) {
        super(repository);
    }

    public List<Topic> findAllBy(TopicType topicType) {
        return repository.findByTopicType(topicType);
    }
}
