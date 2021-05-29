package egl.client.repository.local.statistic;

import egl.client.repository.EntityRepository;
import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;

public interface LocalProfileStatisticRepository extends EntityRepository<ProfileStatistic> {

    ProfileStatistic findByProfile(Profile profile);
}
