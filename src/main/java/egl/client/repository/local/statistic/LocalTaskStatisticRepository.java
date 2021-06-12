package egl.client.repository.local.statistic;

import egl.client.model.core.statistic.TaskStatistic;
import egl.client.repository.core.statistic.TaskStatisticRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalTaskStatisticRepository extends TaskStatisticRepository,
        JpaRepository<TaskStatistic, Long> {
}
