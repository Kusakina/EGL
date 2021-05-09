package egl.client.service.model;

import egl.core.model.DescribedEntity;

import java.util.List;

public interface EntityService<T extends DescribedEntity> {

    List<? extends T> findAll();
}
