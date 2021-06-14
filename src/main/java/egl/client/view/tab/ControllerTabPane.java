package egl.client.view.tab;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import egl.client.controller.Controller;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

public class ControllerTabPane extends TabPane implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    public List<Controller> getControllers() {
        return getTabs().stream()
                .filter(tab -> tab instanceof ControllerTab<?>)
                .map(tab -> (ControllerTab<?>)tab)
                .map(ControllerTab::getController)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void setPrefSize(double width, double height) {
        super.setPrefSize(width, height);

        getControllers().forEach(
            controller -> controller.setPrefSize(
                getPrefWidth(),
                getPrefHeight()
            )
        );
    }
}
