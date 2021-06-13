package egl.client.repository.global.profile;

import egl.client.model.core.profile.Profile;
import egl.client.repository.core.profile.ProfileRepository;
import egl.client.repository.global.GlobalEntityManagerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalProfileRepository
        extends GlobalEntityManagerRepository<Profile>
        implements ProfileRepository {

    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }
}
