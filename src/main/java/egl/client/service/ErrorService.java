package egl.client.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ErrorService {

    public static void showErrorAlert(String message) {
        new Alert(
            Alert.AlertType.ERROR,
            message, ButtonType.OK
        ).show();
    }
}
