package egl.client.controller;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ControllerUtils {

    public static void initializePropertyColumn(
            TableColumn<?, ?> column,
            String propertyName
    ) {
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
    }

    public static <T> void initializeButtonColumn(
            TableColumn<T, Void> buttonColumn,
            String buttonText,
            BiConsumer<ActionEvent, T> buttonEventConsumer
    ) {
        buttonColumn.setCellValueFactory(new PropertyValueFactory<>("none"));

        Callback<TableColumn<T, Void>, TableCell<T, Void>> cellFactory =
                (TableColumn<T, Void> param) -> new TableCell<>() {

                    final Button startButton = new Button(buttonText);

                    @Override
                    public void updateItem(Void fieldValue, boolean empty) {
                        super.updateItem(fieldValue, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            startButton.setOnAction(event -> {
                                T item = getTableView().getItems().get(getIndex());
                                buttonEventConsumer.accept(event, item);
                            });
                            setGraphic(startButton);
                        }
                        setText(null);
                    }
                };

        buttonColumn.setCellFactory(cellFactory);
    }
}
