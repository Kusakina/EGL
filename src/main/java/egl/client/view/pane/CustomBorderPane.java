package egl.client.view.pane;

import egl.client.service.FxmlService;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public class CustomBorderPane extends BorderPane {

    public static final double DEFAULT_SPACING = 20;

    public CustomBorderPane() {
        FxmlService.loadView(this, CustomBorderPane.class);
        setSpacing(DEFAULT_SPACING);
    }

    public double getSpacing() {
        return getPadding().getTop();
    }

    public void setSpacing(double spacing) {
        setPadding(new Insets(spacing));
    }
}
