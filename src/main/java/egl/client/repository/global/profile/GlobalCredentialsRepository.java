package egl.client.repository.global.profile;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.repository.core.EntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GlobalCredentialsRepository extends EntityRepository<Credentials> {

    Optional<Credentials> findByProfile(Profile profile);

    List<Credentials> findAllByLoginAndPasswordHash(String login, long passwordHash);
}
