package egl.client.view.info;

import egl.core.model.DatabaseEntity;

public interface EntityInfoView<T extends DatabaseEntity> {

    void initData(T entity, boolean isCreated);
    void validateData();
    void fillData(T entity);
}
