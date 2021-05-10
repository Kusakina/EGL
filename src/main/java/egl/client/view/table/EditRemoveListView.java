package egl.client.view.table;

import egl.core.model.DescribedEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class EditRemoveListView<T extends DescribedEntity> extends NameSelectListView<T> implements Initializable {

    @FXML private ButtonColumn<T> editColumn;
    @FXML private ButtonColumn<T> removeColumn;

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
        getItems().remove(entity);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOnRemove(this::removeItem);
    }
}
