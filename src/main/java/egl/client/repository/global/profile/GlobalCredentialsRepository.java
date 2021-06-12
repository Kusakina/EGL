package egl.client.repository.global.profile;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.repository.core.EntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalCredentialsRepository extends EntityRepository<Credentials>,
        JpaRepository<Credentials, Long> {

    Credentials findByProfile(Profile profile);

    Credentials findByLogin(String login);
}
