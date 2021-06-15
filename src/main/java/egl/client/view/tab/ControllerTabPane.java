package egl.client.view.tab;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import egl.client.controller.Controller;
import javafx.scene.control.TabPane;

public class ControllerTabPane extends TabPane {

    public ControllerTabPane() {
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    private List<Controller> getControllers() {
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

    public void forEachController(Consumer<Controller> controllerConsumer) {
        getControllers().forEach(controllerConsumer);
    }
}
