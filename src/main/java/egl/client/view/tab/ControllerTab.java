package egl.client.view.tab;

import egl.client.controller.Controller;
import egl.client.service.FxmlService;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;

@NoArgsConstructor
public class ControllerTab<T extends Controller> extends Tab {

    public ControllerTab(String text) {
        super(text);
    }

    @Getter
    private T controller;

    public void setContent(FxmlService fxmlService,
                           Class<? extends T> controllerClass) {
        FxControllerAndView<? extends T, Parent> controllerAndView = fxmlService.load(controllerClass);

        controller = controllerAndView.getController();

        Parent parent = controllerAndView.getView().orElseThrow();
        setContent(parent);
    }
}
