package egl.client.controller.info;

import egl.client.controller.Controller;
import egl.core.model.DatabaseEntity;

public interface EntityInfoController<T extends DatabaseEntity> extends Controller {

    void setContext(T entity, boolean isCreated);

    void validateData();
    T fillData();
}
