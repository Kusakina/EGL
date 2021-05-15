package egl.client.view.table;

import egl.client.service.FxmlService;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomTableView<T> extends TableView<T> implements Initializable {

    public CustomTableView() {
        FxmlService.loadView(this, CustomTableView.class);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
