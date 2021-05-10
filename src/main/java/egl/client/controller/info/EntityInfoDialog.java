package egl.client.controller.info;

import egl.core.model.DatabaseEntity;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class EntityInfoDialog<T extends DatabaseEntity> extends Dialog<T> implements Initializable {

    private final EntityInfoController<T> controller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
        initializeController();
    }

    private void initializeButtons() {
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setOnCloseRequest(dialogEvent -> {
            try {
                controller.validateData();
                controller.prepareToClose();
            } catch (IllegalArgumentException e) {
                dialogEvent.consume();
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
            }
        });

        setResultConverter(buttonType -> {
            if (ButtonType.OK.equals(buttonType)) {
                controller.fillData();
                return controller.getEntity();
            } else {
                return null;
            }
        });
    }

    private void initializeController() {
        var dialogPane = getDialogPane();
        controller.setPrefSize(dialogPane.getWidth(), dialogPane.getHeight());
    }

    public boolean showInfo() {
        controller.prepareToShow();
        return showAndWait().isPresent();
    }
}
