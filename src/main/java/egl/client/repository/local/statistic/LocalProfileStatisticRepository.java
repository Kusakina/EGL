package egl.client.repository.local.statistic;

import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.repository.core.statistic.ProfileStatisticRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalProfileStatisticRepository extends ProfileStatisticRepository,
        JpaRepository<ProfileStatistic, Long> {

}
