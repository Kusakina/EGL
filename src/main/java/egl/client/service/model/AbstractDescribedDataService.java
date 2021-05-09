package egl.client.service.model;

import egl.client.repository.DatabaseDataRepository;
import egl.core.model.DescribedData;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
public abstract class AbstractDescribedDataService<
        T extends DescribedData, RepositoryType extends DatabaseDataRepository<? extends T>
        > implements DescribedDataService<T> {

    protected final RepositoryType repository;

    @Override
    public List<? extends T> findAll() {
        return repository.findAll();
    }
}
