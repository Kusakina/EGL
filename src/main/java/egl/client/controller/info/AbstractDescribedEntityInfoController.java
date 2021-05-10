package egl.client.controller.info;

import egl.core.model.DescribedEntity;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractDescribedEntityInfoController<T extends DescribedEntity> implements EntityInfoController<T> {

    @Getter protected T entity;
    protected boolean isCreated;
    @Setter protected String nameLabelText;

    @Override
    public void setContext(T entity, boolean isCreated) {
        this.entity = entity;
        this.isCreated = isCreated;
    }
}
