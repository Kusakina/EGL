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
import lombok.Setter;
import net.rgielen.fxweaver.core.FxControllerAndView;

@RequiredArgsConstructor
@Getter
public class WindowController implements Controller {

    public static final String CLOSE = "Закрыть";

    @FXML private BorderPane windowBorderPane;
    @FXML private Button closeButton;

    @Setter
    private Stage stage;

    private final Deque<FxControllerAndView<? extends Controller, Parent>> innerRoots = new ArrayDeque<>();
    private final Deque<String> closeButtonTexts = new ArrayDeque<>();
    private final Deque<String> stageTitles = new ArrayDeque<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.closeButton.setOnAction(this::processClose);
    }

    public void setContext(
                           String closeButtonText) {
        this.closeButton.setText(closeButtonText);
    }

    public void open(FxControllerAndView<? extends Controller, Parent> innerRoot,
                     String stageTitle,
                     String closeButtonText) {
        innerRoots.push(innerRoot);
        closeButtonTexts.push(closeButtonText);
        stageTitles.push(stageTitle);
        show();
    }

    public void show() {
        stage.setTitle(stageTitles.peek());
        closeButton.setText(closeButtonTexts.peek());
        windowBorderPane.centerProperty().setValue(getTopRoot().getView().orElseThrow());
        prepareToShow();
        resize();
    }

    private FxControllerAndView<? extends Controller, Parent> getTopRoot() {
        return innerRoots.peek();
    }

    private Controller getTopController() {
        return getTopRoot().getController();
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
        innerRoots.pop();
        closeButtonTexts.pop();
        stageTitles.pop();
    }

    public void processClose(ActionEvent event) {
        prepareToClose();

        if (!innerRoots.isEmpty()) {
            getTopController().refresh();
        } else {
            stage.close();
        }
    }
}
