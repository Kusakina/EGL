package egl.client.view.table;

import egl.core.model.DescribedEntity;
import javafx.fxml.FXML;

import java.util.function.Consumer;

public class DescribedEntitiesListView<T extends DescribedEntity> extends CustomColumnTableView<T> {

    @FXML private PropertyColumn<T, String> nameColumn;
    @FXML private ButtonColumn<T> selectColumn;

    public DescribedEntitiesListView() {
        loadView(DescribedEntitiesListView.class);
    }

    public void setNameTitle(String nameTitle) {
        nameColumn.setText(nameTitle);
    }

    public String getNameTitle() {
        return nameColumn.getText();
    }

    public void setOnSelect(Consumer<T> onSelect) {
        selectColumn.setOnAction(onSelect);
    }
}
