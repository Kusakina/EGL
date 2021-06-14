package egl.client.controller.task;

import java.util.ArrayList;
import java.util.List;

import egl.client.controller.Controller;
import egl.client.model.core.task.Task;
import egl.client.model.core.task.Test;
import egl.client.service.FxmlService;
import egl.client.service.model.local.LocalTestService;
import egl.client.view.tab.ControllerTab;
import egl.client.view.tab.ControllerTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class TestController extends AbstractTaskController {

    private final FxmlService fxmlService;
    private final LocalTestService localTestService;

    @FXML private ControllerTabPane tabPane;
    @FXML private Tab descriptionTab;

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        tabPane.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    protected void prepareToStart() {
        List<Tab> tabs = tabPane.getTabs();
        tabs.clear();

        prepareDescription();
        tabs.add(descriptionTab);

        List<Tab> taskTabs = prepareTasks();
        tabs.addAll(taskTabs);

        tabPane.forEachController(
                Controller::prepareToShow
        );

        tabPane.getSelectionModel().selectFirst();
    }

    private List<Tab> prepareTasks() {
        List<Tab> taskTabs = new ArrayList<>();

        Test test = localTestService.findBy(controllerTask);
        for (Task task : test.getInnerTasks()) {
            var taskTab = new ControllerTab<TaskController>(task.getName());

            var controllerClass = FxmlService.controllerClassWith(
                    task.getSceneName(), TaskController.class
            );

            taskTab.setContent(fxmlService, controllerClass);

            var taskController = taskTab.getController();
            taskController.setContext(task, controllerTopic, result::accumulate);

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
        tabPane.forEachController(
                Controller::prepareToClose
        );
    }
}
