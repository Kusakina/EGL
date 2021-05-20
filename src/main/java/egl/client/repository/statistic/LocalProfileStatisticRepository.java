package egl.client.repository.statistic;

import egl.client.repository.DatabaseEntityRepository;
import egl.core.model.profile.Profile;
import egl.core.model.statistic.ProfileStatistic;

public interface LocalProfileStatisticRepository extends DatabaseEntityRepository<ProfileStatistic> {

    ProfileStatistic findByProfile(Profile profile);
}
