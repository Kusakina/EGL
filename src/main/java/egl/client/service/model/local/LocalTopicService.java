package egl.client.service.model.local;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.local.topic.LocalTopicRepository;
import egl.client.service.model.core.AbstractEntityService;
import egl.client.service.model.core.TopicByLocalService;
import org.springframework.stereotype.Service;

@Service
public class LocalTopicService
        extends AbstractEntityService<Topic, LocalTopicRepository>
        implements TopicByLocalService  {

    public LocalTopicService(LocalTopicRepository repository) {
        super(repository);
    }

    public List<Topic> findAllBy(TopicType topicType) {
        return repository.findByTopicType(topicType);
    }

    @Override
    public Optional<Topic> findTopicByLocal(Topic topic) {
        return Optional.of(topic);
    }
}
