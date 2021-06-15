package egl.client.repository.global.profile;

import java.util.Optional;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.repository.core.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalCredentialsRepository extends EntityRepository<Credentials> {

    Optional<Credentials> findByProfile(Profile profile);

    Optional<Credentials> findByLogin(String login);
}
