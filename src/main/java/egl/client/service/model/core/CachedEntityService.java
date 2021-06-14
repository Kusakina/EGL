package egl.client.service.model.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import egl.client.model.core.DatabaseEntity;
import egl.client.repository.core.EntityRepository;

public abstract class CachedEntityService<
        T extends DatabaseEntity,
        RepositoryType extends EntityRepository<T>,
        IdType extends CachedEntityService.EntityId>
        extends AbstractEntityService<T, RepositoryType> {

    protected interface EntityId { }

    protected final Map<IdType, T> entitiesCache;

    protected CachedEntityService(RepositoryType repository) {
        super(repository);
        this.entitiesCache = new HashMap<>();
    }

    protected abstract Optional<T> findById(IdType entityId);

    protected abstract T createWith(IdType entityId);

    protected abstract IdType getIdOf(T entity);

    protected T findByInPersistence(IdType entityId) {
        var entity = findById(entityId);

        return entity.orElseGet(
                () -> save(createWith(entityId))
        );
    }

    protected T findBy(IdType entityId) {
        return entitiesCache.computeIfAbsent(
                entityId, this::findByInPersistence
        );
    }

    protected Optional<T> tryFindBy(IdType entityId) {
        var entity = entitiesCache.get(entityId);
        if (null != entity) return Optional.of(entity);

        return findById(entityId);
    }

    @Override
    public void remove(T entity) {
        super.remove(entity);
        entitiesCache.remove(getIdOf(entity));
    }
}
