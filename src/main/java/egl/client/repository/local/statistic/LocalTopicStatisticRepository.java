package egl.client.repository.local.statistic;

import egl.client.model.core.statistic.TopicStatistic;
import egl.client.repository.core.statistic.TopicStatisticRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalTopicStatisticRepository extends TopicStatisticRepository,
        JpaRepository<TopicStatistic, Long> {
}
