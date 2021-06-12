package egl.client.repository.local.topic;

import egl.client.model.core.topic.TopicTasks;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.core.task.TopicTasksRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTopicTasksRepository extends TopicTasksRepository,
        JpaRepository<TopicTasks, Long> {

    TopicTasks findByTopicType(TopicType topicType);
}
