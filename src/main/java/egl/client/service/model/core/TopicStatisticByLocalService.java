package egl.client.service.model.core;

import java.util.Optional;

import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;

public interface TopicStatisticByLocalService {

    Optional<TopicStatistic> findStatisticByLocal(Topic topic);
}
