package egl.client.controller.topic;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.controller.task.TaskController;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.service.FxmlService;
import egl.client.service.model.statistic.GlobalStatisticService;
import egl.client.service.model.statistic.LocalStatisticService;
import egl.client.service.model.statistic.StatisticService;
import egl.client.service.model.topic.LocalTopicTasksService;
import egl.client.view.table.list.InfoSelectListView;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class TopicTasksController implements Controller {

    private final FxmlService fxmlService;
    private final LocalTopicTasksService localTopicTasksService;
    private final LocalStatisticService localStatisticService;
    private final GlobalStatisticService globalStatisticService;

    @FXML private InfoSelectListView<Task> tasksListView;
    @FXML private TableColumn<Task, String> taskLocalStatisticColumn;
    @FXML private TableColumn<Task, String> taskGlobalStatisticColumn;

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

        initializeStatisticColumn(taskLocalStatisticColumn, localStatisticService);
        initializeStatisticColumn(taskGlobalStatisticColumn, globalStatisticService);
    }

    private void initializeStatisticColumn(
            TableColumn<Task, String> taskStatisticColumn,
            StatisticService statisticService) {
        taskStatisticColumn.setCellValueFactory(param -> {
            var task = param.getValue();
            var statisticString = getTaskStatistic(statisticService, task);
            return new SimpleStringProperty(statisticString);
        });
    }

    private String getTaskStatistic(StatisticService statisticService, Task task) {
        return statisticService.findBy(controllerTopic)
            .map(topicStatistic -> topicStatistic.getTaskStatisticFor(task))
            .map(taskStatistic -> {
                Result result = taskStatistic.getResult();
                if (Result.NONE == result) {
                    return StatisticService.NO_DATA;
                }

                return String.format(
                        "Лучший результат %d из %d",
                        result.getCorrectAnswers(),
                        result.getTotalAnswers()
                );
            }
        ).orElse(StatisticService.NO_DATA);
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
         */
        updateStatistic(localStatisticService, task, result);
        updateStatistic(globalStatisticService, task, result);
    }

    private void updateStatistic(StatisticService statisticService, Task task, Result result) {
        statisticService.findBy(controllerTopic).ifPresent(topicStatistic -> {
            if (topicStatistic.updateBy(task, result)) {
                statisticService.save(topicStatistic);
            }
        });
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
