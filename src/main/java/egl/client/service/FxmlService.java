package egl.client.service;

import java.io.IOException;

import egl.client.controller.Controller;
import egl.client.controller.WindowController;
import egl.client.controller.info.EntityInfoController;
import egl.client.controller.info.EntityInfoDialog;
import egl.client.model.core.DatabaseEntity;
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

@Service
@RequiredArgsConstructor
public class FxmlService {

    private final FxWeaver fxWeaver;

    private WindowController mainWindow;

    public static <T extends Controller> Class<? extends T> controllerClassWith(
            String controllerClassName, Class<T> controllerParentClass) {
        try {
            String fullControllerClassName = "egl.client.controller." + controllerClassName;
            return Class.forName(fullControllerClassName).asSubclass(controllerParentClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Controller> FxControllerAndView<T, Parent> load(Class<T> controllerClass) {
        return fxWeaver.load(controllerClass);
    }

    public WindowController showWindow() {
        try {
            var fxmlLoader = createFxmlLoader(WindowController.class);

            Parent windowRoot = fxmlLoader.load();

            var windowSize = getWindowSize();
            Scene scene = new Scene(windowRoot, windowSize.getWidth(), windowSize.getHeight());

            Stage stage = new Stage();
            stage.setScene(scene);

            this.mainWindow = fxmlLoader.getController();
            mainWindow.setStage(stage);

            stage.show();
            return mainWindow;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Controller> void showController(
            FxControllerAndView<T, Parent> root,
            String title, String closeButtonText) {
        mainWindow.open(root, title, closeButtonText);
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
            dialog.setTitle(title);

            var dialogPane = dialog.getDialogPane();
            dialogPane.setContent(root);

            var windowSize = getWindowSize();
            dialogPane.setPrefWidth(windowSize.getWidth());
            dialogPane.setPrefHeight(windowSize.getHeight());

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
