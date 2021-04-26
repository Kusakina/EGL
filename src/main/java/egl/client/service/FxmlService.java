package egl.client.service;

import egl.client.controller.Controller;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FxmlService {

    private final FxWeaver fxWeaver;

    public <T extends Controller> T showStage(Class<? extends T> controllerClass,
                                              String title) {
        return showStage(controllerClass, title, new Stage());
    }

    public <T extends Controller> T showStage(Class<? extends T> controllerClass,
                                              String title,
                                              Stage stage) {
        FxControllerAndView<? extends T, Parent> root = fxWeaver.load(controllerClass);
        Scene scene = new Scene(root.getView().orElseThrow(), 800, 800);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();

        return root.getController();
    }
}
