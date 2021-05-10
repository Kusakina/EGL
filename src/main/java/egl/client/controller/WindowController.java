package egl.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;

@RequiredArgsConstructor
@Getter
public class WindowController implements Controller {

    public static final String CLOSE = "Закрыть";

    @FXML private BorderPane windowBorderPane;
    @FXML private Button closeButton;

    private Stage stage;
    private Controller innerController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.closeButton.setOnAction(this::processClose);
        this.windowBorderPane.setPadding(new Insets(20));
    }

    public void setContext(Stage stage,
                           FxControllerAndView<? extends Controller, Parent> innerRoot,
                           String closeButtonText) {
        this.stage = stage;
        this.innerController = innerRoot.getController();
        this.windowBorderPane.centerProperty().setValue(innerRoot.getView().orElseThrow());
        this.closeButton.setText(closeButtonText);
    }

    public void show() {
        prepareToShow();
        stage.show();
        setPrefSize(stage.getWidth(), stage.getHeight());
    }

    @Override
    public void prepareToShow() {
        innerController.prepareToShow();
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        innerController.setPrefSize(
                windowBorderPane.getWidth() * 0.95,
                windowBorderPane.getHeight() * 0.9
        );
    }

    @Override
    public void prepareToClose() {
        innerController.prepareToClose();
    }

    public void processClose(ActionEvent event) {
        prepareToClose();
        stage.close();
    }
}
