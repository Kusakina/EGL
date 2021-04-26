package egl.client;

import egl.client.controller.topic.LocalTopicsListController;
import egl.client.service.FxmlService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxClientApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(SpringClientApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        FxmlService fxmlService = applicationContext.getBean(FxmlService.class);
        var controllerAndView = fxmlService.showStage(LocalTopicsListController.class, "Темы для изучения", stage);
        controllerAndView.getController().show();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}