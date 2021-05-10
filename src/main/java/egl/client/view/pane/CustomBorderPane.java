package egl.client.view.pane;

import egl.client.service.FxmlService;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

import java.util.Arrays;

public class CustomBorderPane extends BorderPane {

    public static final double DEFAULT_SPACING = 10;
    public static final double DEFAULT_MARGIN = 0;

    @Getter private double margin;

    public CustomBorderPane() {
        FxmlService.loadView(this, CustomBorderPane.class);
        setSpacing(DEFAULT_SPACING);
        setMargin(DEFAULT_MARGIN);
    }

    public double getSpacing() {
        return getPadding().getTop();
    }

    public void setSpacing(double spacing) {
        setPadding(new Insets(spacing));
    }

    private void setNodeMargin(Node node) {
        if (null != node) {
            setMargin(node, new Insets(margin));
        }
    }

    public void setMargin(double margin) {
        this.margin = margin;
        Arrays.asList(getCenter(), getTop(), getBottom(), getLeft(), getRight())
                .forEach(this::setNodeMargin);
    }
}
