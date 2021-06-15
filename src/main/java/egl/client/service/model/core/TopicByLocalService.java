package egl.client.service.model.core;

import java.util.Optional;

import egl.client.model.core.topic.Topic;

public interface TopicByLocalService {

    String TOPIC_NOT_REGISTERED = "Не зарегистрирован";

    long findHashByLocal(Topic topic);
    Optional<Topic> findTopicByLocal(Topic topic);
}
