package egl.client.repository.core.statistic;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.repository.core.EntityRepository;

public interface TaskStatisticRepository extends EntityRepository<TaskStatistic> {

    Optional<TaskStatistic> findByTopicStatisticAndTaskName(
            TopicStatistic topicStatistic,
            String taskName
    );

    List<TaskStatistic> findAllByTopicStatistic(TopicStatistic topicStatistic);
}
