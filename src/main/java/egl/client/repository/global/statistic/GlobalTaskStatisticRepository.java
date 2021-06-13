package egl.client.repository.global.statistic;

import java.util.Map;
import java.util.Optional;

import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.repository.core.statistic.TaskStatisticRepository;
import egl.client.repository.global.GlobalEntityManagerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalTaskStatisticRepository
        extends GlobalEntityManagerRepository<TaskStatistic>
        implements TaskStatisticRepository {

    @Override
    protected Class<TaskStatistic> getEntityClass() {
        return TaskStatistic.class;
    }

    @Override
    public Optional<TaskStatistic> findByTopicStatisticAndTaskName(
            TopicStatistic topicStatistic, String taskName) {
        return findByFields(Map.of(
                "topicStatistic", topicStatistic,
                "taskName", taskName
        ));
    }
}
