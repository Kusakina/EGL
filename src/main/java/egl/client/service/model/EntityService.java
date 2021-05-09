package egl.client.service.model;

import egl.core.model.DescribedEntity;

import java.util.List;

public interface EntityService<T extends DescribedEntity> {

    List<T> findAll();

    void save(T entity);
    void remove(T entity);
}
