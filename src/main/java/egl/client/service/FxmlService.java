package egl.client.service;

import egl.client.controller.Controller;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FxmlService {

    private final FxWeaver fxWeaver;

    public FxControllerAndView<? extends Controller, Parent> showStage(
            String controllerClassName,
            String title
    ) {
        return showStage(controllerClassWith(controllerClassName), title);
    }

    public <T extends Controller> FxControllerAndView<? extends T, Parent> showStage(
            Class<? extends T> controllerClass,
            String title) {
        return showStage(controllerClass, title, new Stage());
    }

    public <T extends Controller> FxControllerAndView<? extends T, Parent> showStage(
            Class<? extends T> controllerClass,
            String title,
            Stage stage
    ) {
        FxControllerAndView<? extends T, Parent> root = load(controllerClass);
        return showStage(title, stage, root);
    }

    private <T extends Controller> FxControllerAndView<? extends T, Parent> showStage(
            String title, Stage stage,
            FxControllerAndView<? extends T, Parent> root) {
        Scene scene = new Scene(root.getView().orElseThrow(), 800, 800);

        stage.setScene(scene);
        root.getController().setStage(stage);

        stage.setTitle(title);
        stage.show();

        return root;
    }

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
}
