package egl.client.repository.global.profile;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalCredentialsRepository extends JpaRepository<Credentials, Long> {

    Credentials findByProfile(Profile profile);

    Credentials findByLogin(String login);
}
