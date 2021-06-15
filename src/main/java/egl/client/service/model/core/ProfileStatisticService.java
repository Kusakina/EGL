package egl.client.service.model.core;

import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.repository.core.statistic.ProfileStatisticRepository;
import egl.client.service.model.EntityServiceException;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public abstract class ProfileStatisticService
        extends CachedEntityService<
            ProfileStatistic,
            ProfileStatisticRepository,
            ProfileStatisticService.ProfileStatisticId
        > {

    @Data
    @RequiredArgsConstructor
    static class ProfileStatisticId implements EntityId {

        private final Profile profile;
    }

    protected ProfileStatisticService(ProfileStatisticRepository repository) {
        super(repository);
    }

    @Override
    protected Optional<ProfileStatistic> findById(ProfileStatisticId profileStatisticId) {
        try {
            return repository.findByProfile(
                    profileStatisticId.getProfile()
            );
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }

    @Override
    protected ProfileStatistic createWith(ProfileStatisticId profileStatisticId) {
        return new ProfileStatistic(
                profileStatisticId.getProfile()
        );
    }

    @Override
    protected ProfileStatisticId getIdOf(ProfileStatistic profileStatistic) {
        return new ProfileStatisticId(profileStatistic.getProfile());
    }

    public ProfileStatistic findBy(Profile profile) {
        var profileStatisticId = new ProfileStatisticId(profile);
        return findBy(profileStatisticId);
    }
}
