package egl.client.repository.local.statistic;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.repository.EntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalProfileStatisticRepository extends EntityRepository<ProfileStatistic> {

    ProfileStatistic findByProfile(Profile profile);
}
