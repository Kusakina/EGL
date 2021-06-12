package egl.client.repository.global.statistic;

import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.repository.core.statistic.ProfileStatisticRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalProfileStatisticRepository extends ProfileStatisticRepository,
        JpaRepository<ProfileStatistic, Long> {

}
