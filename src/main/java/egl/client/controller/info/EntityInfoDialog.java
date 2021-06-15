package egl.client.controller.info;

import egl.client.model.core.DatabaseEntity;
import egl.client.service.ErrorService;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class EntityInfoDialog<T extends DatabaseEntity> extends Dialog<T> {

    private final EntityInfoController<T> controller;

    public EntityInfoDialog(EntityInfoController<T> controller) {
        this.controller = controller;
        initialize();
    }

    public void initialize() {
        initializeButtons();
        initializeController();
    }

    private void initializeButtons() {
        getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

        setOnCloseRequest(dialogEvent -> {
            try {
                if (null != getResult()) controller.prepareToClose();
            } catch (IllegalArgumentException e) {
                dialogEvent.consume();
                ErrorService.showErrorAlert(e.getMessage());
            }
        });

        setResultConverter(buttonType -> {
            if (ButtonType.OK.equals(buttonType)) {
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
