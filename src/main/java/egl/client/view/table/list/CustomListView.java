package egl.client.view.table.list;

import egl.client.service.FxmlService;
import egl.client.view.table.CustomTableView;
import egl.client.view.table.column.ButtonColumn;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public abstract class CustomListView<T> extends CustomTableView<T> {

    @FXML protected TableColumn<T, String> nameColumn;
    @FXML protected ButtonColumn<T> selectColumn;
    @FXML protected ButtonColumn<T> editColumn;
    @FXML protected ButtonColumn<T> removeColumn;

    public CustomListView() {
        FxmlService.loadView(this, CustomListView.class);
    }

    public void setItems(List<T> items) {
        getItems().setAll(items);
    }

    public void setNameTitle(String nameTitle) {
        nameColumn.setText(nameTitle);
    }

    public String getNameTitle() {
        return nameColumn.getText();
    }

    public void setOnSelect(Consumer<T> onSelect) { selectColumn.setOnAction(onSelect); }

    public void removeItem(T entity) {
        getItems().remove(entity);
    }

    public void setOnEdit(Consumer<T> onEdit) {
        editColumn.setOnAction(onEdit);
    }

    public void setOnRemove(Consumer<T> onRemove) {
        removeColumn.setOnAction(onRemove);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        setOnRemove(this::removeItem);
    }
}
