package egl.client.controller.topic;

import egl.client.controller.Controller;
import egl.client.controller.task.TaskController;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.LocalProfileService;
import egl.client.service.model.statistic.LocalStatisticService;
import egl.client.service.model.topic.LocalTopicTasksService;
import egl.client.view.table.list.InfoSelectListView;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
@RequiredArgsConstructor
public class TopicTasksController implements Controller {

    private final FxmlService fxmlService;
    private final LocalTopicTasksService localTopicTasksService;
    private final LocalProfileService localProfileService;
    private final LocalStatisticService localStatisticService;

    @FXML private InfoSelectListView<Task> tasksListView;
    @FXML private TableColumn<Task, String> taskStatisticColumn;

    private Topic controllerTopic;

    public void setContext(Topic topic) {
        this.controllerTopic = topic;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTasks();
    }

    public void initializeTasks() {
        tasksListView.setOnSelect(this::onSelect);

        taskStatisticColumn.setCellValueFactory(param -> {
            var task = param.getValue();
            var statisticString = getTaskStatistic(task);
            return new SimpleStringProperty(statisticString);
        });
    }

    private String getTaskStatistic(Task task) {
        var profile = localProfileService.getSelectedProfile();
        if (null == profile) return "Нет данных";

        TopicStatistic topicStatistic = localStatisticService.findBy(profile, controllerTopic);
        TaskStatistic taskStatistic = topicStatistic.getTaskStatisticFor(task);

        Result result = taskStatistic.getResult();
        if (Result.NONE == result) {
            return "Результатов не зафиксировано";
        }

        return String.format("Лучший результат %d из %d", result.getCorrectAnswers(), result.getTotalAnswers());
    }

    private void onSelect(Task task) {
        var taskRoot = fxmlService.load(task.getSceneName());

        var taskController = (TaskController) taskRoot.getController();
        taskController.setContext(task, controllerTopic, (result) -> tryUpdateStatistic(task, result));

        fxmlService.showStage(
                taskRoot, task.getName(), TaskController.FINISH_BUTTON_TEXT
        );
    }

    private void tryUpdateStatistic(Task task, Result result) {
        /*
         FIXME show dialog with question
         local/global profiles
         can select/login right there
         */
        var localProfile = localProfileService.getSelectedProfile();
        if (null == localProfile) {
            return;
        }

        TopicStatistic topicStatistic = localStatisticService.findBy(localProfile, controllerTopic);
        if (topicStatistic.updateBy(task, result)) {
            localStatisticService.save(topicStatistic);
        }
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        tasksListView.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    public void prepareToShow() {
        var tableTasks = tasksListView.getItems();
        tableTasks.clear();

        var topicTasks = localTopicTasksService.findBy(controllerTopic.getTopicType());
        tableTasks.add(topicTasks.getTheoryTask());
        tableTasks.addAll(topicTasks.getTasks());
        tableTasks.add(topicTasks.getTest().getTask());
    }

    @Override
    public void prepareToClose() {

    }
}
