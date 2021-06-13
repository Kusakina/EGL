package egl.client.repository.core;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.DatabaseEntity;

public interface EntityRepository<T extends DatabaseEntity> {

    <S extends T> S save(S value);
    void delete(T value);

    Optional<T> findById(Long id);
    List<T> findAll();
}
