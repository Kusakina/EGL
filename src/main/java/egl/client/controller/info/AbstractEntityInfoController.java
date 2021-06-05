package egl.client.controller.info;

import egl.client.view.info.EntityInfoView;
import egl.client.model.core.DatabaseEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractEntityInfoController<T extends DatabaseEntity> implements EntityInfoController<T> {

    @Getter protected T entity;
    protected boolean isCreated;

    protected final List<EntityInfoView<T>> innerInfoViews;

    protected AbstractEntityInfoController() {
        this.innerInfoViews = new ArrayList<>();
    }

    @SafeVarargs
    protected final void addInnerInfoViews(EntityInfoView<T>... innerInfoViews) {
        this.innerInfoViews.addAll(Arrays.asList(innerInfoViews));
    }

    @Override
    public void setContext(T entity, boolean isCreated) {
        this.entity = entity;
        this.isCreated = isCreated;
    }

    @Override
    public void validateData() {
        innerInfoViews.forEach(EntityInfoView::validateData);
    }

    @Override
    public void fillData() {
        innerInfoViews.forEach(infoView -> infoView.fillData(entity));
    }

    @Override
    public void prepareToShow() {
        innerInfoViews.forEach(infoView -> infoView.initData(entity, isCreated));
    }

    @Override
    public void prepareToClose() {
        validateData();
        fillData();
    }
}
