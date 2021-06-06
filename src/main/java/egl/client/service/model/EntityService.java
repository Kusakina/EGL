package egl.client.service.model;

import egl.client.model.core.DatabaseEntity;

import java.util.List;

public interface EntityService<T extends DatabaseEntity> {

    List<T> findAll();

    void save(T entity);
    void remove(T entity);
}
