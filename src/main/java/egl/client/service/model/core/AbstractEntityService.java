package egl.client.service.model.core;

import java.util.List;

import egl.client.model.core.DatabaseEntity;
import egl.client.repository.core.EntityRepository;
import egl.client.service.model.EntityService;
import egl.client.service.model.EntityServiceException;
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
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }

    @Override
    public T save(T entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }

    @Override
    public void remove(T entity) {
        try {
            repository.delete(entity);
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }
}
