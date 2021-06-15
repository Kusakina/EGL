package egl.client.controller.profile;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.model.core.statistic.RatingInfo;
import egl.client.service.FxmlService;
import egl.client.view.table.list.CustomListView;
import javafx.fxml.Initializable;

public class RatingListView extends CustomListView<RatingInfo> implements Initializable {

    public RatingListView() {
        FxmlService.loadView(this, RatingListView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }
}
