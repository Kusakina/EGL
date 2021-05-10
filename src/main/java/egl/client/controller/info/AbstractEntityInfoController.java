package egl.client.controller.info;

import egl.core.model.DatabaseEntity;
import lombok.Getter;

public abstract class AbstractEntityInfoController<T extends DatabaseEntity> implements EntityInfoController<T> {

    @Getter protected T entity;
    protected boolean isCreated;

    @Override
    public void setContext(T entity, boolean isCreated) {
        this.entity = entity;
        this.isCreated = isCreated;
    }
}
