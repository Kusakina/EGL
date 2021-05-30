package egl.client.repository.local.topic;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.EntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalTopicRepository extends EntityRepository<Topic> {

    List<Topic> findByTopicType(TopicType topicType);
}
