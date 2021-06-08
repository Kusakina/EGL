package egl.client.controller.task;

import java.util.ArrayList;
import java.util.List;

import egl.client.controller.Controller;
import egl.client.model.core.task.Task;
import egl.client.model.core.task.Test;
import egl.client.service.FxmlService;
import egl.client.service.model.task.LocalTestService;
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
public class TestController extends AbstractTaskController {

    private final FxmlService fxmlService;
    private final LocalTestService localTestService;

    @FXML private TabPane tabPane;
    @FXML private Tab descriptionTab;

    private List<TaskController> taskControllers;

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        tabPane.setPrefSize(parentWidth, parentHeight);
        for (TaskController taskController : taskControllers) {
            taskController.setPrefSize(tabPane.getPrefWidth(), tabPane.getPrefHeight());
        }
    }

    @Override
    protected void prepareToStart() {
        List<Tab> tabs = tabPane.getTabs();
        tabs.clear();

        prepareDescription();
        tabs.add(descriptionTab);

        List<Tab> taskTabs = prepareTasks();
        tabs.addAll(taskTabs);

        tabPane.getSelectionModel().selectFirst();
    }

    private List<Tab> prepareTasks() {
        this.taskControllers = new ArrayList<>();
        List<Tab> taskTabs = new ArrayList<>();

        Test test = localTestService.findBy(controllerTask);
        for (Task task : test.getInnerTasks()) {
            FxControllerAndView<? extends Controller, Parent> controllerAndView = fxmlService.load(task.getSceneName());

            TaskController taskController = (TaskController) controllerAndView.getController();
            taskController.setContext(task, controllerTopic, result::accumulate);
            taskController.prepareToShow();
            taskControllers.add(taskController);

            Tab taskTab = new Tab(task.getName());
            Parent taskParent = controllerAndView.getView().orElseThrow();
            taskTab.contentProperty().setValue(taskParent);
            taskTabs.add(taskTab);
        }

        return taskTabs;
    }

    private void prepareDescription() {
        String testDescription = String.format(
                "Тест по теме %s\n\n%s",
                controllerTopic.getName(),
                controllerTask.getDescription()
        );
        descriptionTextArea.setText(testDescription);
    }

    @Override
    protected void prepareToFinish() {
        for (TaskController taskController : taskControllers) {
            taskController.prepareToClose();
        }
    }
}
