package egl.client.service;

import egl.client.controller.Controller;
import egl.client.controller.WindowController;
import egl.client.controller.info.EntityInfoController;
import egl.client.controller.info.EntityInfoDialog;
import egl.core.model.DatabaseEntity;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

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
            var fxmlLoader = createFxmlLoader(WindowController.class);

            Parent windowRoot = fxmlLoader.load();

            var windowSize = getWindowSize();
            Scene scene = new Scene(windowRoot, windowSize.getWidth(), windowSize.getHeight());

            stage.setScene(scene);
            stage.setTitle(title);

            var windowController = fxmlLoader.<WindowController>getController();
            windowController.setContext(stage, innerRoot, closeButtonText);
            windowController.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Rectangle2D getWindowSize() {
        var screenSize = Screen.getPrimary().getVisualBounds();
        return new Rectangle2D(0, 0, screenSize.getWidth() * 0.8, screenSize.getHeight() * 0.8);
    }

    public <T extends DatabaseEntity,
            ControllerType extends EntityInfoController<T>
            > boolean showInfoDialog(
            Class<ControllerType> controllerClass, T entity,
            String title, boolean isCreated) {
        try {
            var loader = createFxmlLoader(controllerClass);

            Parent root = loader.load();

            var controller = loader.<ControllerType>getController();
            controller.setContext(entity, isCreated);

            var dialog = new EntityInfoDialog<>(controller);
            dialog.getDialogPane().setContent(root);
            dialog.setTitle(title);

            var windowSize = getWindowSize();
            dialog.setWidth(windowSize.getWidth());
            dialog.setHeight(windowSize.getHeight());

            return dialog.showInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FXMLLoader createFxmlLoader(Class<?> controllerClass) {
        String fxmlFilePath = controllerClass.getName().replace(".", "/");
        return new FXMLLoader(
                controllerClass.getResource(
                        String.format("/%s.fxml", fxmlFilePath)
                )
        );
    }

    public static <T> void loadView(T view, Class<? super T> controllerClass) {
        loadView(view, controllerClass, false);
    }

    public static <T> void loadView(T view, Class<? super T> controllerClass, boolean debug) {
        var loader = createFxmlLoader(controllerClass);

        loader.setRoot(view);
        loader.setController(view);

        try {
            loader.load();
        } catch (IOException e) {
            if (debug || !(e.getCause() instanceof NullPointerException)) {
                e.printStackTrace();
            }
        }
    }
}
