package egl.client.view.table.list;

import egl.client.model.core.DescribedEntity;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityService;
import egl.client.service.model.EntityServiceException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.Setter;

public class InfoSelectEditRemoveListView<T extends DescribedEntity> extends InfoSelectListView<T> {

    @Setter private EntityService<T> service;

    public InfoSelectEditRemoveListView() {
        FxmlService.loadView(this, InfoSelectEditRemoveListView.class);
    }

    public void showItems() {
        try {
            var entities = service.findAll();
            setItems(entities);
        } catch (EntityServiceException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Проблема при загрузке списка сущностей",
                    ButtonType.OK).show();
        }
    }

    @Override
    public void removeItem(T entity) {
        super.removeItem(entity);
        service.remove(entity);
    }
}
