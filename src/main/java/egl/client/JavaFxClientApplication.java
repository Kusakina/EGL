package egl.client;

import egl.client.controller.TopicsListController;
//import egl.client.service.FxmlService;
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
        fxmlService.showStage(TopicsListController.class, "Темы для изучения", stage);
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}