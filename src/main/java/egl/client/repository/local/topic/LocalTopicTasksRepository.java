package egl.client.repository.local.topic;

import egl.client.model.core.topic.TopicTasks;
import egl.client.model.core.topic.TopicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("localTransactionManager")
public interface LocalTopicTasksRepository extends JpaRepository<TopicTasks, Long> {

    TopicTasks findByTopicType(TopicType topicType);
}
