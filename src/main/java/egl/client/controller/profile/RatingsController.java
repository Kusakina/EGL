package egl.client.controller.profile;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

import egl.client.controller.Controller;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.RatingInfo;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.StatisticService;
import egl.client.service.model.core.TopicByLocalService;
import egl.client.service.model.local.LocalTopicService;
import egl.client.service.model.local.LocalTopicTasksService;
import egl.client.view.pane.CustomBorderPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RatingsController implements Controller {

    private static final DateTimeFormatter LAST_UPDATE_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss dd:MM:yyyy");

    private final LocalTopicService localTopicService;
    private final TopicByLocalService topicByLocalService;

    private final LocalTopicTasksService localTopicTasksService;

    private final StatisticService statisticService;

    @FXML
    private CustomBorderPane mainBorderPane;

    @FXML
    private ListView<Topic> topicsListView;

    @FXML
    private Button tryRefreshRatingsButton;

    @FXML
    private Text refreshRatingsInformationText;

    @FXML
    private TabPane taskRatingsTabPane;

    private List<ProfileStatistic> profileStatistics;
    private Map<Topic, Map<Task, Tab>> topicTabs;

    private boolean triedToUpdate;
    private LocalDateTime lastUpdateDateTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.profileStatistics = new ArrayList<>();
        this.topicTabs = new HashMap<>();

        this.triedToUpdate = false;
        this.lastUpdateDateTime = null;

        topicsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        topicsListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTopic, newTopic) -> showRatingsFor(newTopic)
        );

        tryRefreshRatingsButton.setOnAction(event -> tryRefreshRatings());
    }

    private void tryRefreshRatings() {
        try {
            refreshRatings();
            lastUpdateDateTime = LocalDateTime.now();
            refreshRatingsInformationText.setText("");
        } catch (EntityServiceException e) {
            refreshRatingsInformationText.setText("Проблема при обновлении глобальных результатов.");
        }

        if (null != lastUpdateDateTime) {
            refreshRatingsInformationText.setText(String.format(
                    "%s. Показаны результаты от %s",
                    refreshRatingsInformationText.getText(),
                    LAST_UPDATE_DATE_TIME_FORMATTER.format(lastUpdateDateTime)
            ));
        }
    }

    private void refreshRatings() {
        profileStatistics = statisticService.findAllProfileStatistics();

        var topics = localTopicService.findAll().stream()
                .map(topicByLocalService::findTopicByLocal)
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .collect(Collectors.toUnmodifiableList());

        for (Topic topic : topics) {
            var topicTabs = this.topicTabs.computeIfAbsent(
                    topic, this::createTopicTabs
            );

            topicTabs.forEach((task, tab) -> {
                var taskRatings = refreshTaskRatings(topic, task);

                var ratingListView = (RatingListView) tab.getContent();
                ratingListView.getItems().setAll(taskRatings);
            });
        }

        topicsListView.getItems().setAll(topics);
        topicsListView.getSelectionModel().selectFirst();
    }

    private Map<Task, Tab> createTopicTabs(Topic topic) {
        var topicTasks = localTopicTasksService.findBy(topic.getTopicType());

        var tasks = new ArrayList<>(topicTasks.getTasks());
        tasks.add(topicTasks.getTest().getTask());

        return tasks.stream()
                .collect(Collectors.toUnmodifiableMap(
                        Function.identity(),
                        this::createTaskRatingTab
                ));
    }

    private Tab createTaskRatingTab(Task task) {
        var ratingListView = new RatingListView();

        Tab tab = new Tab(task.getName());
        tab.setContent(ratingListView);

        return tab;
    }

    private List<RatingInfo> refreshTaskRatings(Topic topic, Task task) {
        return profileStatistics.stream()
                .map(profileStatistic ->
                        statisticService.tryFindBy(
                                profileStatistic,
                                topic,
                                task
                        )
                ).filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .map(RatingInfo::new)
                .filter(RatingInfo::hasScores)
                .sorted()
                .limit(20) // TODO made as const
                .collect(Collectors.toUnmodifiableList());
    }

    private void showRatingsFor(Topic topic) {
        if (null == topic) {
            return;
        }

        var tabs = topicTabs.get(topic).values();
        taskRatingsTabPane.getTabs().setAll(tabs);
    }

    @Override
    public void prepareToShow() {
        if (!triedToUpdate) {
            tryRefreshRatings();
            triedToUpdate = true;
        }
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        mainBorderPane.setPrefSize(parentWidth, parentHeight);
    }
}
