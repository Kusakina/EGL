package egl.client.controller.topic;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.controller.task.TaskController;
import egl.client.model.topic.LocalTopic;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.LocalProfileService;
import egl.client.service.model.statistic.LocalStatisticService;
import egl.client.view.table.list.InfoSelectListView;
import egl.core.model.profile.Profile;
import egl.core.model.statistic.Result;
import egl.core.model.statistic.TopicStatistic;
import egl.core.model.task.Task;
import javafx.fxml.FXML;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class TopicTasksController implements Controller {

    private final FxmlService fxmlService;
    private final LocalProfileService localProfileService;
    private final LocalStatisticService localStatisticService;

    @FXML private InfoSelectListView<Task> tasksListView;

    private LocalTopic controllerTopic;

    public void setContext(LocalTopic topic) {
        this.controllerTopic = topic;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTasks();
    }

    public void initializeTasks() {
        tasksListView.setOnSelect(this::onSelect);
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
        Profile localProfile = localProfileService.getSelectedProfile();
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

        var topicType = controllerTopic.getTopicType();
        tableTasks.add(topicType.getTheoryTask());
        tableTasks.addAll(topicType.getTasks());
        tableTasks.add(topicType.getTest());
    }

    @Override
    public void prepareToClose() {

    }
}
