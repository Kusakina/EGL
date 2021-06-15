package egl.client.repository.core.statistic;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.repository.core.EntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskStatisticRepository extends EntityRepository<TaskStatistic> {

    Optional<TaskStatistic> findByTopicStatisticAndTaskName(
            TopicStatistic topicStatistic,
            String taskName
    );

    List<TaskStatistic> findAllByTopicStatistic(TopicStatistic topicStatistic);

    @Query("select ts from TaskStatistic ts where ts.topicStatistic.topic.id in :topicIds")
    List<TaskStatistic> findAllBy(@Param("topicIds") List<Long> topicIds);
}
