package egl.client.view.table.column;

import javafx.beans.NamedArg;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class PropertyColumn<T, PropertyType> extends TableColumn<T, PropertyType> {

    public PropertyColumn(@NamedArg("text") String title, @NamedArg("property") String propertyName) {
        super(title);
        setCellValueFactory(new PropertyValueFactory<>(propertyName));
    }
}
