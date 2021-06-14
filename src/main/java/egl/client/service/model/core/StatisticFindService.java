package egl.client.service.model.core;

import java.util.Optional;

import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;

public interface StatisticFindService {

    Optional<TopicStatistic> findBy(Topic topic);
}
