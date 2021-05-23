package egl.client.service.model;

import egl.client.repository.EntityRepository;
import egl.core.model.DatabaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void save(T entity) {
        repository.save(entity);
    }

    @Override
    public void remove(T entity) {
        repository.delete(entity);
    }
}
