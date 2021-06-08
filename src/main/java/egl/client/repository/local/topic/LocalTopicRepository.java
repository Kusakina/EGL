package egl.client.repository.local.topic;

import java.util.List;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.core.task.TopicRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTopicRepository extends TopicRepository {

    List<Topic> findByTopicType(TopicType topicType);
}
