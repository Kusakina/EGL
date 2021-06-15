package egl.client.controller.profile;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.service.FxmlService;
import egl.client.view.tab.ControllerTab;
import egl.client.view.tab.ControllerTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class StatisticsController implements Controller {

    private final FxmlService fxmlService;

    private final Class<? extends ProfileSelectController> profileSelectControllerClass;
    private final Class<? extends RatingsController> ratingsControllerClass;

    @FXML
    private ControllerTabPane activitiesTabPane;

    @FXML
    private ControllerTab<ProfileSelectController> profileSelectTab;

    @FXML
    private ControllerTab<RatingsController> ratingsTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activitiesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        profileSelectTab.setContent(fxmlService, profileSelectControllerClass);
        ratingsTab.setContent(fxmlService, ratingsControllerClass);
    }

    @Override
    public void prepareToShow() {
        activitiesTabPane.forEachController(Controller::prepareToShow);
    }

    @Override
    public void prepareToClose() {
        activitiesTabPane.forEachController(Controller::prepareToClose);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        activitiesTabPane.setPrefSize(parentWidth, parentHeight);
    }
}
