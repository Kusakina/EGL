package egl.client.repository.global.profile;

import java.util.Optional;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.repository.global.GlobalEntityManagerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalCredentialsRepository
        extends GlobalEntityManagerRepository<Credentials> {

    @Override
    protected Class<Credentials> getEntityClass() {
        return Credentials.class;
    }

    public Optional<Credentials> findByProfile(Profile profile) {
        return getByField("profile", profile);
    }

    public Optional<Credentials> findByLogin(String login) {
        return getByField("login", login);
    }
}
