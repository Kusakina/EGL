package egl.client.controller.topic;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.controller.ControllerUtils;
import egl.client.controller.task.TaskController;
import egl.client.model.topic.LocalTopic;
import egl.client.service.FxmlService;
import egl.core.model.task.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class LocalTopicController extends Controller {

    private final FxmlService fxmlService;

    @FXML private TableView<Task> tasksListTableView;
    @FXML private TableColumn<Task, String> taskNameColumn;
    @FXML private TableColumn<Task, Void> taskStartColumn;

    @FXML private Button backButton;

    private LocalTopic controllerTopic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> this.closeWindow());

        initializeColumns();
    }

    private void initializeColumns() {
        ControllerUtils.initializePropertyColumn(taskNameColumn, "name");

        ControllerUtils.initializeButtonColumn(
                taskStartColumn,
                "Запуск",
                (event, task) -> {
                    var controllerAndView = fxmlService.showStage(task.getSceneName(), task.getName());

                    var taskController = (TaskController) controllerAndView.getController();
                    taskController.start(task, controllerTopic, (result) -> {}); // FIXME would sent data
                }
        );
    }

    public void show(LocalTopic topic) {
        this.controllerTopic = topic;
        showTasks();
    }

    private void showTasks() {
        var topicType = controllerTopic.getTopicType();
        var tableTasks = tasksListTableView.getItems();

        tableTasks.clear();
        tableTasks.add(topicType.getTheoryTask());
        tableTasks.addAll(topicType.getTasks());
        tableTasks.add(topicType.getTest());
    }
}
