package egl.client.view.table;

import egl.client.model.topic.LocalTopic;
import egl.client.service.model.EntityService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class EditRemoveListView<T extends LocalTopic> extends NameSelectListView<T> implements Initializable {

    @FXML private ButtonColumn<T> editColumn;
    @FXML private ButtonColumn<T> removeColumn;

    @Setter private EntityService<T> service;

    public EditRemoveListView() {
        loadView(EditRemoveListView.class);
    }

    public void setOnEdit(Consumer<T> onEdit) {
        editColumn.setOnAction(onEdit);
    }

    public void setOnRemove(Consumer<T> onRemove) {
        removeColumn.setOnAction(onRemove);
    }

    public void removeItem(T entity) {
        service.remove(entity);
        getItems().remove(entity);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOnRemove(this::removeItem);
    }
}
