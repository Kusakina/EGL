package egl.client.repository.global.statistic;

import egl.client.model.core.statistic.TopicStatistic;
import egl.client.repository.core.statistic.TopicStatisticRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalTopicStatisticRepository extends TopicStatisticRepository,
        JpaRepository<TopicStatistic, Long> {
}
