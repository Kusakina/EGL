package egl.client.controller.topic;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.controller.task.TaskController;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.service.ErrorService;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.AbstractStatisticService;
import egl.client.service.model.core.StatisticService;
import egl.client.service.model.core.TopicByLocalService;
import egl.client.service.model.global.GlobalStatisticService;
import egl.client.service.model.global.GlobalTopicService;
import egl.client.service.model.local.LocalStatisticService;
import egl.client.service.model.local.LocalTopicService;
import egl.client.service.model.local.LocalTopicTasksService;
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
    private final LocalTopicService localTopicService;
    private final LocalStatisticService localStatisticService;
    private final GlobalTopicService globalTopicService;
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

        initializeStatisticColumn(taskLocalStatisticColumn, localStatisticService, localTopicService);
        initializeStatisticColumn(taskGlobalStatisticColumn, globalStatisticService, globalTopicService);
    }

    private void initializeStatisticColumn(
            TableColumn<Task, String> taskStatisticColumn,
            StatisticService statisticService,
            TopicByLocalService topicByLocalService) {
        taskStatisticColumn.setCellValueFactory(param -> {
            var task = param.getValue();
            var statisticString = getTaskStatistic(statisticService, topicByLocalService, task);
            return new SimpleStringProperty(statisticString);
        });
    }

    private String getTaskStatistic(StatisticService statisticService, 
                                    TopicByLocalService topicByLocalService,
                                    Task task) {
        try {
            var topicOptional = topicByLocalService.findTopicByLocal(controllerTopic);
            if (topicOptional.isEmpty()) {
                return TopicByLocalService.TOPIC_NOT_REGISTERED;
            }

            return topicOptional.flatMap(statisticService::findBy)
                    .map(topicStatistic -> statisticService.findBy(topicStatistic, task))
                    .map(taskStatistic -> {
                                Result result = taskStatistic.getResult();
                                if (Result.NONE.equals(result)) {
                                    return AbstractStatisticService.NO_DATA;
                                }

                                return String.format(
                                        "Лучший результат %s",
                                        result.toString()
                                );
                            }
                    ).orElse(AbstractStatisticService.NO_DATA);
        } catch (EntityServiceException e) {
            return AbstractStatisticService.NO_DATA;
        }
    }

    private void onSelect(Task task) {
        var taskControllerClass = FxmlService.controllerClassWith(
                task.getSceneName(), TaskController.class
        );
        var taskRoot = fxmlService.load(taskControllerClass);

        var taskController = (TaskController) taskRoot.getController();
        taskController.setContext(task, controllerTopic, (result) -> tryUpdateStatistic(task, result));

        fxmlService.showController(
                taskRoot, task.getName(), TaskController.FINISH_BUTTON_TEXT
        );
    }

    private void tryUpdateStatistic(Task task, Result result) {
        updateStatistic(localStatisticService, localTopicService, task, result);
        updateStatistic(globalStatisticService, globalTopicService, task, result);
    }

    private void updateStatistic(StatisticService statisticService, 
                                 TopicByLocalService topicByLocalService,
                                 Task task, Result result) {
        try {
            topicByLocalService.findTopicByLocal(controllerTopic)
                    .flatMap(statisticService::findBy)
                    .ifPresent(topicStatistic -> {
                        statisticService.update(topicStatistic, task, result);
                        tasksListView.refresh();
                    });
        } catch (EntityServiceException e) {
            ErrorService.showErrorAlert(
                    "Проблема подключения к базе данных при обновлении результатов"
            );
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

    @Override
    public void refresh() {
        tasksListView.refresh();
    }
}
