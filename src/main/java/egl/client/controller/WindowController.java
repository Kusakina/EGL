package egl.client.controller;

import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private final Deque<Controller> innerControllers = new ArrayDeque<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.closeButton.setOnAction(this::processClose);
    }

    public void setContext(Stage stage,
                           String closeButtonText) {
        this.stage = stage;
        this.closeButton.setText(closeButtonText);
    }

    public void open(FxControllerAndView<? extends Controller, Parent> innerRoot) {
        this.windowBorderPane.centerProperty().setValue(innerRoot.getView().orElseThrow());

        var innerController = innerRoot.getController();
        innerControllers.push(innerController);

        show();
    }

    public void show() {
        prepareToShow();
        if (!stage.isShowing()) stage.show();
        resize();
    }

    private Controller getTopController() {
        return innerControllers.peek();
    }

    @Override
    public void prepareToShow() {
        getTopController().prepareToShow();
    }

    private void resize() {
        setPrefSize(stage.getWidth(), stage.getHeight());
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        getTopController().setPrefSize(
                windowBorderPane.getWidth() * 0.95,
                windowBorderPane.getHeight() * 0.9
        );
    }

    @Override
    public void prepareToClose() {
        getTopController().prepareToClose();
        innerControllers.pop();
    }

    public void processClose(ActionEvent event) {
        prepareToClose();

        if (!innerControllers.isEmpty()) {
            show();
        } else {
            stage.close();
        }
    }
}
