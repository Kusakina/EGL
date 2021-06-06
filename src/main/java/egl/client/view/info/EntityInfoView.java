package egl.client.view.info;

import egl.client.model.core.DatabaseEntity;

public interface EntityInfoView<T extends DatabaseEntity> {

    void initData(T entity, boolean isCreated);
    void validateData();
    void fillData(T entity);
}
