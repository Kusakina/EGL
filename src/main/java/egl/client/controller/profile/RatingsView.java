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
import java.util.function.Supplier;
import java.util.stream.Collectors;

import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.RatingInfo;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicTasks;
import egl.client.model.core.topic.TopicType;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityServiceException;
import egl.client.utils.TriFunction;
import egl.client.view.pane.CustomBorderPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Text;
import lombok.Setter;

public class RatingsView extends CustomBorderPane implements Initializable {

    private static final DateTimeFormatter LAST_UPDATE_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss dd:MM:yyyy");

    @FXML
    private ListView<Topic> topicsListView;

    @FXML
    private Button tryRefreshRatingsButton;

    @FXML
    private Text refreshRatingsInformationText;

    @FXML
    private TabPane taskRatingsTabPane;

    private final Map<Topic, Map<Task, Tab>> topicsTabs;

    private List<ProfileStatistic> profileStatistics;
    private LocalDateTime lastUpdateDateTime;

    @Setter
    private Supplier<List<Topic>> freshTopicsSupplier;

    @Setter
    private Supplier<List<ProfileStatistic>> profileStatisticsSupplier;

    @Setter
    private Function<TopicType, TopicTasks> topicTasksGetter;

    @Setter
    private TriFunction<ProfileStatistic, Topic, Task, Optional<TaskStatistic>> taskStatisticGetter;

    public RatingsView() {
        FxmlService.loadView(this, RatingsView.class);

        this.topicsTabs = new HashMap<>();
        this.lastUpdateDateTime = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topicsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        topicsListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTopic, newTopic) -> showRatingsFor(newTopic)
        );

        tryRefreshRatingsButton.setOnAction(event -> tryRefreshRatings());
    }

    private void showRatingsFor(Topic topic) {
        if (null == topic) {
            return;
        }

        var tabs = topicsTabs.get(topic).values();
        taskRatingsTabPane.getTabs().setAll(tabs);
    }

    public void tryRefreshRatings() {
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
        this.profileStatistics = profileStatisticsSupplier.get();

        var topics = freshTopicsSupplier.get();

        for (Topic topic : topics) {
            var topicTabs = topicsTabs.computeIfAbsent(
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
        var topicTasks = topicTasksGetter.apply(topic.getTopicType());

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
                .map(profileStatistic -> taskStatisticGetter.apply(profileStatistic, topic, task))
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .map(RatingInfo::new)
                .filter(RatingInfo::hasScores)
                .sorted()
                .limit(20) // TODO made as const
                .collect(Collectors.toUnmodifiableList());
    }
}
