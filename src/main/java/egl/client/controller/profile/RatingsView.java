package egl.client.controller.profile;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import egl.client.model.core.statistic.RatingInfo;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicTasks;
import egl.client.model.core.topic.TopicType;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.TaskStatisticService;
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
            DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");

    @FXML
    private ListView<Topic> topicsListView;

    @FXML
    private Button tryRefreshRatingsButton;

    @FXML
    private Text refreshRatingsInformationText;

    @FXML
    private TabPane taskRatingsTabPane;

    private final Map<Topic, Map<Task, Tab>> topicsTabs;

    private LocalDateTime lastUpdateDateTime;

    @Setter
    private Supplier<List<Topic>> freshTopicsSupplier;

    @Setter
    private Function<List<Topic>, List<TaskStatistic>> allTaskStatisticsGetter;

    @Setter
    private Function<TopicType, TopicTasks> topicTasksGetter;

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
        var topics = freshTopicsSupplier.get();

        Map<TopicType, List<Task>> topicTypeTasks = topics.stream()
                .map(Topic::getTopicType)
                .distinct()
                .collect(Collectors.toUnmodifiableMap(
                        Function.identity(),
                        topicType -> {
                            var topicTasks = topicTasksGetter.apply(topicType);
                            var tasks = topicTasks.getTasks();
                            tasks.add(topicTasks.getTest().getTask());
                            return tasks;
                        }
                ));

        Map<Topic, Map<Task, List<TaskStatistic>>> groupedTaskStatistics = new HashMap<>();
        allTaskStatisticsGetter.apply(topics).forEach(taskStatistic -> {
            var topic = taskStatistic.getTopicStatistic().getTopic();
            var topicTaskStatistics = groupedTaskStatistics.computeIfAbsent(
                    topic, key -> new HashMap<>()
            );

            var taskName = taskStatistic.getTaskName();

            var topicTasks = topicTypeTasks.get(topic.getTopicType());
            topicTasks.stream()
                .filter(topicTask -> TaskStatisticService.getTaskName(topicTask).equals(taskName))
                .findAny()
                .ifPresent(task ->
                    topicTaskStatistics.computeIfAbsent(
                            task, key -> new ArrayList<>()
                    ).add(taskStatistic)
                );
        });

        for (Topic topic : topics) {
            var topicType = topic.getTopicType();
            var topicTasks = topicTypeTasks.getOrDefault(topicType, List.of());

            var topicTabs = topicsTabs.computeIfAbsent(
                    topic, key -> createTopicTabs(topicTasks)
            );

            var topicTaskStatistics = groupedTaskStatistics.getOrDefault(
                    topic, Map.of()
            );

            topicTabs.forEach((task, tab) -> {
                var taskStatistics = topicTaskStatistics.getOrDefault(task, List.of());
                var taskRatings = refreshTaskRatings(taskStatistics);

                var ratingListView = (RatingListView) tab.getContent();
                ratingListView.getItems().setAll(taskRatings);
            });
        }

        topicsListView.getItems().setAll(topics);
        topicsListView.getSelectionModel().selectFirst();
    }

    private Map<Task, Tab> createTopicTabs(List<Task> tasks) {
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

    private List<RatingInfo> refreshTaskRatings(List<TaskStatistic> taskStatistics) {
        return taskStatistics.stream()
                .map(RatingInfo::new)
                .filter(RatingInfo::hasScores)
                .sorted()
                .limit(20) // TODO made as const
                .collect(Collectors.toUnmodifiableList());
    }
}
