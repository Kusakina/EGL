package egl.client.repository.core;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import egl.client.model.core.DatabaseEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class EntityManagerRepository<T extends DatabaseEntity> implements EntityRepository<T> {

    private final Class<T> entityClass;

    protected abstract EntityManager getEntityManager();

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
        return Optional.of(getEntityManager().find(entityClass, id));
    }

    @Override
    public List<T> findAll() {
        throw new UnsupportedOperationException();
    }
}
