package egl.client.service.model.core;

import java.util.Optional;

import egl.client.model.core.topic.Topic;

public interface TopicByLocalService {

    Optional<Long> findGlobalIdByLocal(Topic topic);
    Optional<Topic> findTopicByLocal(Topic topic);
}
