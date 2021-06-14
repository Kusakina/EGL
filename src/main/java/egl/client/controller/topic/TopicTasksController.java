package egl.client.controller.topic;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.controller.task.TaskController;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.AbstractStatisticService;
import egl.client.service.model.core.StatisticService;
import egl.client.service.model.core.TopicStatisticByLocalService;
import egl.client.service.model.global.GlobalStatisticByLocalService;
import egl.client.service.model.global.GlobalStatisticService;
import egl.client.service.model.local.LocalStatisticService;
import egl.client.service.model.local.LocalTopicTasksService;
import egl.client.view.table.list.InfoSelectListView;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    private final GlobalStatisticByLocalService localToGlobalStatisticService;

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

        initializeStatisticColumn(taskLocalStatisticColumn, localStatisticService, localStatisticService);
        initializeStatisticColumn(taskGlobalStatisticColumn, globalStatisticService, localToGlobalStatisticService);
    }

    private void initializeStatisticColumn(
            TableColumn<Task, String> taskStatisticColumn,
            StatisticService statisticService,
            TopicStatisticByLocalService statisticFindService) {
        taskStatisticColumn.setCellValueFactory(param -> {
            var task = param.getValue();
            var statisticString = getTaskStatistic(statisticService, statisticFindService, task);
            return new SimpleStringProperty(statisticString);
        });
    }

    private String getTaskStatistic(StatisticService statisticService, 
                                    TopicStatisticByLocalService statisticFindService,
                                    Task task) {
        try {
            return statisticFindService.findStatisticByLocal(controllerTopic)
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
            new Alert(Alert.AlertType.ERROR,
                    "Проблема при загрузке результатов",
                    ButtonType.OK).show();

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
        /*
         FIXME show dialog with question
         local/global profiles
         */
        updateStatistic(localStatisticService, localStatisticService, task, result);
        updateStatistic(globalStatisticService, localToGlobalStatisticService, task, result);
    }

    private void updateStatistic(StatisticService statisticService, 
                                 TopicStatisticByLocalService statisticFindService,
                                 Task task, Result result) {
        try {
            statisticFindService.findStatisticByLocal(controllerTopic)
                    .ifPresent(topicStatistic -> {
                        statisticService.update(topicStatistic, task, result);
                        tasksListView.refresh();
                    });
        } catch (EntityServiceException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Проблема при обновлении результатов",
                    ButtonType.OK).show();
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
