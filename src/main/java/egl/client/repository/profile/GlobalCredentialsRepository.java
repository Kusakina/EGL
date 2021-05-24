package egl.client.repository.profile;

import egl.client.model.profile.GlobalCredentials;
import egl.client.repository.EntityRepository;
import egl.core.model.profile.Profile;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalCredentialsRepository extends EntityRepository<GlobalCredentials> {

    GlobalCredentials findByProfile(Profile profile);
}
