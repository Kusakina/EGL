package egl.client.view.table.list;

import egl.client.model.core.DescribedEntity;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityService;
import lombok.Setter;

public class InfoSelectEditRemoveListView<T extends DescribedEntity> extends InfoSelectListView<T> {

    @Setter private EntityService<T> service;

    public InfoSelectEditRemoveListView() {
        FxmlService.loadView(this, InfoSelectEditRemoveListView.class);
    }

    public void showItems() {
        setItems(service.findAll());
    }
}
