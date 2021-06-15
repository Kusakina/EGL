package egl.client.repository.core.statistic;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.repository.core.EntityRepository;

public interface TopicStatisticRepository extends EntityRepository<TopicStatistic> {

    Optional<TopicStatistic> findByProfileStatisticAndTopic(
            ProfileStatistic profileStatistic, Topic topic
    );

    List<TopicStatistic> findAllByTopic(Topic topic);
}
