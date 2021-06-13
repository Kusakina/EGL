package egl.client.repository.global.profile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import egl.client.model.core.profile.Profile;
import egl.client.repository.core.EntityManagerRepository;
import egl.client.repository.core.profile.ProfileRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalProfileRepository
        extends EntityManagerRepository<Profile>
        implements ProfileRepository {

    public GlobalProfileRepository() {
        super(Profile.class);
    }

    @PersistenceContext(unitName = "global")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
