package egl.client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import egl.client.service.FxmlService;
import egl.model.Task;
import egl.model.Test;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class TestController extends TaskController {

    @FXML public TabPane tabPane;
    @FXML public Tab descriptionTab;
    @FXML public TextArea descriptionTextArea;

    private final FxmlService fxmlService;

    private List<TaskController> taskControllers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    @Override
    protected void startTask(Task testAsTask) {
        Test test = (Test) testAsTask;

        List<Tab> tabs = tabPane.getTabs();
        tabs.clear();

        String testDescription = String.format(
                "Тест по теме %s\n\n%s",
                this.topic.getName(),
                test.getDescription()
        );
        descriptionTextArea.setText(testDescription);
        tabs.add(descriptionTab);

        this.taskControllers = new ArrayList<>();

        for (Task task : test.getTasks()) {
            FxControllerAndView<? extends Controller, Parent> controllerAndView = fxmlService.load(task.getSceneName());

            Tab taskTab = new Tab(task.getName());

            Parent taskParent = controllerAndView.getView().orElseThrow();
            taskTab.contentProperty().setValue(taskParent);

            tabs.add(taskTab);

            TaskController taskController = (TaskController) controllerAndView.getController();
            taskControllers.add(taskController);

            taskController.start(task, topic, result::accumulate);
        }
    }

    @Override
    protected void finishTask() {
        for (TaskController taskController : taskControllers) {
            taskController.finishTask();
        }
    }
}
