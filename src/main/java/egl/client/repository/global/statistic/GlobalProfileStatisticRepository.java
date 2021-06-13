package egl.client.repository.global.statistic;

import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.repository.core.statistic.ProfileStatisticRepository;
import egl.client.repository.global.GlobalEntityManagerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalProfileStatisticRepository
        extends GlobalEntityManagerRepository<ProfileStatistic>
        implements ProfileStatisticRepository {

    @Override
    protected Class<ProfileStatistic> getEntityClass() {
        return ProfileStatistic.class;
    }

    @Override
    public Optional<ProfileStatistic> findByProfile(Profile profile) {
        return findByField("profile", profile);
    }
}
