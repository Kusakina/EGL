package egl.client.service.model.core;

import java.util.List;

import egl.client.model.core.DatabaseEntity;
import egl.client.repository.core.EntityRepository;
import egl.client.service.model.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public abstract class AbstractEntityService<
        T extends DatabaseEntity, RepositoryType extends EntityRepository<T>
        > implements EntityService<T> {

    protected final RepositoryType repository;

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void remove(T entity) {
        repository.delete(entity);
    }
}
