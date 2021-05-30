package egl.client.service.model.topic;

import egl.client.model.core.topic.TopicTasks;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.local.topic.LocalTopicTasksRepository;
import egl.client.service.model.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class LocalTopicTasksService extends AbstractEntityService<TopicTasks, LocalTopicTasksRepository> {

    public LocalTopicTasksService(LocalTopicTasksRepository repository) {
        super(repository);
    }

    public TopicTasks findBy(TopicType topicType) {
        return repository.findByTopicType(topicType);
    }
}
