package egl.client.service.model;

import java.util.List;

import egl.client.model.core.DatabaseEntity;

public interface EntityService<T extends DatabaseEntity> {

    List<T> findAll() throws EntityServiceException;

    T save(T entity);
    void remove(T entity);
}
