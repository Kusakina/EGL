package egl.client.view.table.column;

import javafx.beans.NamedArg;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
public class ButtonColumn<T> extends TableColumn<T, Void> {

    private Consumer<T> onAction;

    public ButtonColumn(@NamedArg("text") String buttonText) {
        Callback<TableColumn<T, Void>, TableCell<T, Void>> cellFactory = createCellFactory(buttonText);
        setCellFactory(cellFactory);

        this.onAction = (T) -> {};
    }

    private Callback<TableColumn<T, Void>, TableCell<T, Void>> createCellFactory(String buttonText) {
        return (TableColumn<T, Void> param) -> new TableCell<>() {

            final Button startButton = new Button(buttonText);

            @Override
            public void updateItem(Void fieldValue, boolean empty) {
                super.updateItem(fieldValue, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    startButton.setOnAction(event -> {
                        T item = getTableView().getItems().get(getIndex());
                        onAction.accept(item);
                    });
                    setGraphic(startButton);
                }
                setText(null);
            }
        };
    }
}
