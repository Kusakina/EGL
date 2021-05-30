package egl.client.repository.local.topic;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalTopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByTopicType(TopicType topicType);
}
