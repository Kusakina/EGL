package egl.client.repository.global.topic;

import egl.client.model.core.topic.Topic;
import egl.client.repository.core.task.TopicRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalTopicRepository extends TopicRepository,
        JpaRepository<Topic, Long> {

}
