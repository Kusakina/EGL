package egl.client.service;

import java.io.IOException;

import egl.client.controller.Controller;
import egl.client.controller.WindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FxmlService {

    private final FxWeaver fxWeaver;

    public static Class<? extends Controller> controllerClassWith(String controllerClassName) {
        try {
            String fullControllerClassName = "egl.client.controller." + controllerClassName;
            return Class.forName(fullControllerClassName).asSubclass(Controller.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public FxControllerAndView<? extends Controller, Parent> load(String controllerClassName) {
        return load(controllerClassWith(controllerClassName));
    }

    public <T extends Controller> FxControllerAndView<? extends T, Parent> load(Class<? extends T> controllerClass) {
        return fxWeaver.load(controllerClass);
    }

    public <T extends Controller> void showStage(
            FxControllerAndView<? extends T, Parent> innerRoot,
            String title, String closeButtonText) {
        showStage(innerRoot, title, closeButtonText, new Stage());
    }

    public <T extends Controller> void showStage(
            FxControllerAndView<? extends T, Parent> innerRoot,
            String title, String closeButtonText,
            Stage stage) {
        try {
            var fxmlLoader = new FXMLLoader(getClass().getResource("/egl/client/controller/WindowController.fxml"));

            Parent windowRoot = fxmlLoader.load();

            var screenSize = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(windowRoot, screenSize.getWidth() * 0.8, screenSize.getHeight() * 0.8);

            stage.setScene(scene);
            stage.setTitle(title);

            var windowController = fxmlLoader.<WindowController>getController();
            windowController.setContext(stage, innerRoot, closeButtonText);
            windowController.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
