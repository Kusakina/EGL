package egl.client.repository.global.profile;

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

    public Credentials findByProfile(Profile profile) {
        return getEntityManager().createQuery(
                "SELECT credentials from Credentials credentials where credentials.profile = :profile",
                getEntityClass()
        ).setParameter("profile", profile).getSingleResult();
    }

    public Credentials findByLogin(String login) {
        return getEntityManager().createQuery(
                "SELECT credentials from Credentials credentials where credentials.login = :login",
                getEntityClass()
        ).setParameter("login", login).getSingleResult();
    }
}
