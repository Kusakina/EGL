package egl.client.repository.global.statistic;

import egl.client.model.core.statistic.TaskStatistic;
import egl.client.repository.core.statistic.TaskStatisticRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalTaskStatisticRepository extends TaskStatisticRepository,
        JpaRepository<TaskStatistic, Long> {
}
