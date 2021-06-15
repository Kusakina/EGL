package egl.client.service.model.local;

import egl.client.model.core.topic.TopicTasks;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.local.topic.LocalTopicTasksRepository;
import egl.client.service.model.core.AbstractEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("localTransactionManager")
public class LocalTopicTasksService extends AbstractEntityService<TopicTasks, LocalTopicTasksRepository> {

    public LocalTopicTasksService(LocalTopicTasksRepository repository) {
        super(repository);
    }

    public TopicTasks findBy(TopicType topicType) {
        return repository.findByTopicType(topicType);
    }
}
