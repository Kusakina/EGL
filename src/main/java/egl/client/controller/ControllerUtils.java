package egl.client.controller;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
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

    public static void rescaleTableView(Stage stage, TableView<?> tableView, double widthCoeff, double heightCoeff) {
        rescaleRegion(stage, tableView, widthCoeff, heightCoeff);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        double columnWidth = tableView.getPrefWidth() / tableView.getColumns().size();
        tableView.getColumns().forEach(column -> column.setPrefWidth(columnWidth));
    }

    public static void rescaleRegion(Stage stage, Region node, double widthCoeff, double heightCoeff) {
        node.setPrefWidth(stage.getWidth() * widthCoeff);
        node.setPrefHeight(stage.getHeight() * heightCoeff);
    }
}
