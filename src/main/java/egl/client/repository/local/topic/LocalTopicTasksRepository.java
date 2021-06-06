package egl.client.repository.local.topic;

import egl.client.model.core.topic.TopicTasks;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.core.EntityRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("localTransactionManager")
public interface LocalTopicTasksRepository extends EntityRepository<TopicTasks> {

    TopicTasks findByTopicType(TopicType topicType);
}
