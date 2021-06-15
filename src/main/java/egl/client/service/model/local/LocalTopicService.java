package egl.client.service.model.local;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.local.topic.LocalTopicRepository;
import egl.client.service.model.core.TopicService;
import org.springframework.stereotype.Service;

@Service
public class LocalTopicService
        extends TopicService<LocalTopicRepository> {

    public LocalTopicService(LocalTopicRepository repository,
                             LocalTopicInfoService localTopicInfoService) {
        super(repository, localTopicInfoService);
    }

    public List<Topic> findAllBy(TopicType topicType) {
        return repository.findByTopicType(topicType);
    }

    @Override
    public Optional<Topic> findTopicByLocal(Topic topic) {
        return Optional.of(topic);
    }
}
