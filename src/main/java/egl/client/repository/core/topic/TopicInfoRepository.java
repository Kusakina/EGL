package egl.client.repository.core.topic;

import java.util.Optional;

import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicInfo;
import egl.client.repository.core.EntityRepository;

public interface TopicInfoRepository<T extends TopicInfo> extends EntityRepository<T> {

    T findByTopic(Topic topic);
    Optional<T> findByTopicHashCode(long topicHashCode);
}
