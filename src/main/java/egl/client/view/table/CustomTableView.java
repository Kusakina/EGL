package egl.client.view.table;

import egl.client.service.FxmlService;
import javafx.scene.control.TableView;

import java.util.List;

public class CustomTableView<T> extends TableView<T> {

    public CustomTableView() {
        FxmlService.loadView(this, CustomTableView.class);
    }

    public void showItems(List<T> items) {
        this.getItems().setAll(items);
    }

    private void rescaleColumns() {
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        var columns = getColumns();
        if (!columns.isEmpty()) {
            double columnWidth = getPrefWidth() / columns.size();
            columns.forEach(column -> column.setPrefWidth(columnWidth));
        }
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        super.setPrefSize(parentWidth, parentHeight);
        rescaleColumns();
    }
}
