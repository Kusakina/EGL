package egl.client.service.model;

import egl.core.model.DatabaseEntity;

import java.util.List;

public interface EntityService<T extends DatabaseEntity> {

    List<T> findAll();

    void save(T entity);
    void remove(T entity);
}
