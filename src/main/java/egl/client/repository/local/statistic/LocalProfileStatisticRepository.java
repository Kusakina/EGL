package egl.client.repository.local.statistic;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalProfileStatisticRepository extends JpaRepository<ProfileStatistic, Long> {

    ProfileStatistic findByProfile(Profile profile);
}
