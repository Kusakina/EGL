package egl.client.repository.local.statistic;

import egl.client.repository.EntityRepository;
import egl.core.model.profile.Profile;
import egl.core.model.statistic.ProfileStatistic;

public interface LocalProfileStatisticRepository extends EntityRepository<ProfileStatistic> {

    ProfileStatistic findByProfile(Profile profile);
}
