package egl.client.view;

import egl.client.controller.ControllerUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CustomColumnTableView<T> extends TableView<T> {

    public <ColumnType> void addColumn(String title, Consumer<TableColumn<T, ColumnType>> columnInitializer) {
        TableColumn<T, ColumnType> column = new TableColumn<>(title);
        columnInitializer.accept(column);
        this.getColumns().add(column);
    }

    public void addPropertyColumn(String title, String propertyName) {
        this.<String>addColumn(title, (column) -> ControllerUtils.initializePropertyColumn(column, propertyName));
    }

    public void addButtonColumn(String title, BiConsumer<ActionEvent, T> onAction) {
        addButtonColumn(title, title, onAction);
    }

    public void addButtonColumn(String title, String buttonText, BiConsumer<ActionEvent, T> onAction) {
        this.<Void>addColumn(title, (column) -> ControllerUtils.initializeButtonColumn(column, buttonText, onAction));
    }

    public void setItems(List<T> items) {
        this.getItems().setAll(items);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        super.setPrefSize(parentWidth, parentHeight);
        ControllerUtils.rescaleTableView(this);
    }
}
