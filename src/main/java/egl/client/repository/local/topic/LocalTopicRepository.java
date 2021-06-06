package egl.client.repository.local.topic;

import java.util.List;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.repository.core.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTopicRepository extends EntityRepository<Topic> {

    List<Topic> findByTopicType(TopicType topicType);
}
