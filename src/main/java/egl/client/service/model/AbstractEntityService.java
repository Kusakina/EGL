package egl.client.service.model;

import egl.client.model.core.DatabaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
public abstract class AbstractEntityService<
        T extends DatabaseEntity, RepositoryType extends JpaRepository<T, Long>
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
