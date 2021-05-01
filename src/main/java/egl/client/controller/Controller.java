package egl.client.controller;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.Setter;

@Setter
public abstract class Controller implements Initializable {

    protected Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;

        rescaleViews(stage.getWidth(), stage.getHeight());
    }

    protected Controller() {
        this.stage = null;
    }

    protected boolean isWindow() {
        return null != stage;
    }

    protected void closeWindow() {
        if (isWindow()) {
            stage.close();
        }
    }

    public abstract void rescaleViews(double parentWidth, double parentHeight);
}
