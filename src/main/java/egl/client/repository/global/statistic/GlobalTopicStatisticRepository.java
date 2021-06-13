package egl.client.repository.global.statistic;

import java.util.Map;
import java.util.Optional;

import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.repository.core.statistic.TopicStatisticRepository;
import egl.client.repository.global.GlobalEntityManagerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalTopicStatisticRepository
        extends GlobalEntityManagerRepository<TopicStatistic>
        implements TopicStatisticRepository {

    @Override
    protected Class<TopicStatistic> getEntityClass() {
        return TopicStatistic.class;
    }

    @Override
    public Optional<TopicStatistic> findByProfileStatisticAndTopic(
            ProfileStatistic profileStatistic, Topic topic) {
        return findByFields(Map.of(
            "profileStatistic", profileStatistic,
            "topic", topic
        ));
    }
}
