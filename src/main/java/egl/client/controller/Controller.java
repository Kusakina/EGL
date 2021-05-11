package egl.client.controller;

import javafx.fxml.Initializable;

public interface Controller extends Initializable {

    void setPrefSize(double parentWidth, double parentHeight);
    void prepareToShow();
    void prepareToClose();
}
