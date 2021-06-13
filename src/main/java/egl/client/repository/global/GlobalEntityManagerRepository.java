package egl.client.repository.global;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import egl.client.model.core.DatabaseEntity;
import egl.client.repository.core.EntityManagerRepository;

public abstract class GlobalEntityManagerRepository<T extends DatabaseEntity>
        extends EntityManagerRepository<T> {

    @PersistenceContext(unitName = "global")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
