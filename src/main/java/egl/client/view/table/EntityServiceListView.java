package egl.client.view.table;

import egl.client.service.FxmlService;
import egl.client.service.model.EntityService;
import egl.core.model.DescribedEntity;
import lombok.Setter;

public class EntityServiceListView<T extends DescribedEntity> extends EditRemoveListView<T> {

    @Setter private EntityService<T> service;

    public EntityServiceListView() {
        FxmlService.loadView(this, EntityServiceListView.class);
    }

    public void showItems() {
        showItems(service.findAll());
    }

    @Override
    public void removeItem(T entity) {
        super.removeItem(entity);
        service.remove(entity);
    }
}
