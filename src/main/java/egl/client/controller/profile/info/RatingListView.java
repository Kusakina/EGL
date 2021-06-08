package egl.client.controller.profile.info;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.model.core.statistic.RatingInfo;
import egl.client.view.table.column.PropertyColumn;
import egl.client.view.table.list.CustomListView;
import javafx.fxml.Initializable;

public class RatingListView extends CustomListView<RatingInfo> implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        //noinspection unchecked
        getInfoColumns().addAll(
            new PropertyColumn<>("Игрок", "name"),
            new PropertyColumn<>("Результат", "result")
        );
    }
}
