package egl.client.repository.core.statistic;

import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.repository.core.EntityRepository;

public interface ProfileStatisticRepository extends EntityRepository<ProfileStatistic> {

    Optional<ProfileStatistic> findByProfile(Profile profile);
}
