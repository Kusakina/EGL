package egl.client.repository.global.profile;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import egl.client.model.core.profile.Profile;
import egl.client.repository.core.profile.ProfileRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalProfileRepository implements ProfileRepository {

    @PersistenceContext(unitName = "global")
    private EntityManager entityManager;

    @Override
    public <S extends Profile> S save(S value) {
        return entityManager.merge(value);
    }

    @Override
    public void delete(Profile value) {
        entityManager.remove(value);
    }

    @Override
    public Optional<Profile> getById(Long id) {
        return Optional.of(entityManager.find(Profile.class, id));
    }

    @Override
    public List<Profile> findAll() {
        throw new UnsupportedOperationException();
    }
}
