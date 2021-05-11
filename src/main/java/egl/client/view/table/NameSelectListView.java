package egl.client.view.table;

import egl.client.service.FxmlService;
import egl.core.model.DescribedEntity;
import javafx.fxml.FXML;

import java.util.function.Consumer;

public class NameSelectListView<T extends DescribedEntity> extends CustomTableView<T> {

    @FXML private PropertyColumn<T, String> nameColumn;
    @FXML private ButtonColumn<T> selectColumn;

    public NameSelectListView() {
        FxmlService.loadView(this, NameSelectListView.class);
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
