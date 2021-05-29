package egl.client.controller.info;

import egl.client.controller.Controller;
import egl.client.model.core.DatabaseEntity;

public interface EntityInfoController<T extends DatabaseEntity> extends Controller {

    void setContext(T entity, boolean isCreated);
    T getEntity();

    void validateData();
    void fillData();
}
