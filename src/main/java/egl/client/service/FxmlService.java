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
        Scene scene = new Scene(root.getView().orElseThrow(), 800, 800);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();

        return root;
    }

    public FxControllerAndView<? extends Controller, Parent> load(String controllerClassName) {
        try {
            Class<? extends Controller> controllerClass = Class.forName(controllerClassName).asSubclass(Controller.class);
            return load(controllerClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Controller> FxControllerAndView<? extends T, Parent> load(Class<? extends T> controllerClass) {
        return fxWeaver.load(controllerClass);
    }
}
