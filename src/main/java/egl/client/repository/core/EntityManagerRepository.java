package egl.client.repository.core;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import egl.client.model.core.DatabaseEntity;

public abstract class EntityManagerRepository<T extends DatabaseEntity> implements EntityRepository<T> {

    protected abstract EntityManager getEntityManager();

    protected abstract Class<T> getEntityClass();

    @Override
    public <S extends T> S save(S value) {
        return getEntityManager().merge(value);
    }

    @Override
    public void delete(T value) {
        getEntityManager().remove(value);
    }

    @Override
    public Optional<T> getById(Long id) {
        var result = getEntityManager().find(
                getEntityClass(), id
        );

        return Optional.of(result);
    }

    @Override
    public List<T> findAll() {
        throw new UnsupportedOperationException();
    }
}
