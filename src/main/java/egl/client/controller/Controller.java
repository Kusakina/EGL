package egl.client.controller;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.Setter;

@Setter
public abstract class Controller implements Initializable {

    protected Stage stage;

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
}
