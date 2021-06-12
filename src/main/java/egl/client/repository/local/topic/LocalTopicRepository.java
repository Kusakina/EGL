package egl.client.repository.local.topic;

import java.util.List;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.core.task.TopicRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTopicRepository extends TopicRepository,
        JpaRepository<Topic, Long> {

    List<Topic> findByTopicType(TopicType topicType);
}
