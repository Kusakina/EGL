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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class LocalTopicController implements Controller {

    private final FxmlService fxmlService;

    @FXML private TableView<Task> tasksListTableView;
    @FXML private TableColumn<Task, String> taskNameColumn;
    @FXML private TableColumn<Task, Void> taskStartColumn;

    private LocalTopic controllerTopic;

    public void setContext(LocalTopic topic) {
        this.controllerTopic = topic;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
    }

    private void initializeColumns() {
        ControllerUtils.initializePropertyColumn(taskNameColumn, "name");

        ControllerUtils.initializeButtonColumn(
                taskStartColumn,
                "Запуск",
                (event, task) -> {
                    var taskRoot = fxmlService.load(task.getSceneName());

                    var taskController = (TaskController) taskRoot.getController();
                    taskController.setContext(task, controllerTopic, (result) -> {}); // FIXME would sent data

                    fxmlService.showStage(
                            taskRoot, task.getName(), TaskController.FINISH_BUTTON_TEXT
                    );
                }
        );
    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {
        tasksListTableView.setPrefSize(parentWidth, parentHeight);
        ControllerUtils.rescaleTableView(tasksListTableView);
    }

    @Override
    public void prepareToShow() {
        var topicType = controllerTopic.getTopicType();
        var tableTasks = tasksListTableView.getItems();

        tableTasks.clear();
        tableTasks.add(topicType.getTheoryTask());
        tableTasks.addAll(topicType.getTasks());
        tableTasks.add(topicType.getTest());
    }

    @Override
    public void prepareToClose() {

    }
}
