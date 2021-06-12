package egl.client.repository.global.topic;

import egl.client.model.core.topic.TopicTasks;
import egl.client.repository.core.task.TopicTasksRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalTopicTasksRepository extends TopicTasksRepository,
        JpaRepository<TopicTasks, Long> { }
