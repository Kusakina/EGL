package egl.client.controller.task;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.controller.ControllerUtils;
import egl.client.service.FxmlService;
import egl.core.model.task.Task;
import egl.core.model.task.Test;
import egl.core.model.topic.Topic;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class TestController extends TaskController {

    private final FxmlService fxmlService;

    @FXML private TabPane tabPane;
    @FXML private Tab descriptionTab;

    private List<TaskController> taskControllers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        descriptionTab.setText("Информация");
    }

    private void rescaleViews() {
        ControllerUtils.rescaleRegion(stage, tabPane, 0.8, 0.8);
    }

    @Override
    protected void prepareToStart(Task controllerTask, Topic controllerTopic) {
        rescaleViews();

        Test test = (Test) controllerTask;

        List<Tab> tabs = tabPane.getTabs();
        tabs.clear();

        prepareDescription(controllerTopic, test);
        tabs.add(descriptionTab);

        prepareTasks(controllerTopic, test, tabs);

        tabPane.getSelectionModel().selectFirst();
    }

    private void prepareTasks(Topic controllerTopic, Test test, List<Tab> tabs) {
        this.taskControllers = new ArrayList<>();

        for (Task task : test.getTasks()) {
            FxControllerAndView<? extends Controller, Parent> controllerAndView = fxmlService.load(task.getSceneName());

            Tab taskTab = new Tab(task.getName());

            Parent taskParent = controllerAndView.getView().orElseThrow();
            taskTab.contentProperty().setValue(taskParent);

            tabs.add(taskTab);

            TaskController taskController = (TaskController) controllerAndView.getController();
            taskControllers.add(taskController);

            taskController.start(task, controllerTopic, result::accumulate);
        }
    }

    private void prepareDescription(Topic controllerTopic, Test test) {
        String testDescription = String.format(
                "Тест по теме %s\n\n%s",
                controllerTopic.getName(),
                test.getDescription()
        );
        descriptionTextArea.setText(testDescription);
    }

    @Override
    protected void prepareToFinish() {
        for (TaskController taskController : taskControllers) {
            taskController.finish();
        }
    }
}
