package egl.client.service.model;

import egl.client.repository.DatabaseDataRepository;
import egl.core.model.DescribedEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
public abstract class AbstractEntityService<
        T extends DescribedEntity, RepositoryType extends DatabaseDataRepository<? extends T>
        > implements EntityService<T> {

    protected final RepositoryType repository;

    @Override
    public List<? extends T> findAll() {
        return repository.findAll();
    }
}
