package egl.client.controller.profile;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import egl.client.controller.profile.info.RatingListView;
import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.RatingInfo;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.GlobalCredentialsService;
import egl.client.service.model.profile.GlobalProfileService;
import egl.client.service.model.statistic.GlobalStatisticService;
import egl.client.service.model.topic.LocalTopicService;
import egl.client.service.model.topic.LocalTopicTasksService;
import egl.client.view.text.LabeledTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class GlobalProfilesController extends ProfileSelectController {

    private final GlobalCredentialsService globalCredentialsService;
    private final GlobalStatisticService globalStatisticService;
    private final LocalTopicService localTopicService;
    private final LocalTopicTasksService localTopicTasksService;

    @FXML
    private TabPane activitiesTabPane;

    @FXML
    private Tab globalProfileTab;

    @FXML
    private GridPane loginGridPane;
    @FXML
    private Text loginInfoText;
    @FXML
    private LabeledTextField loginTextField;
    @FXML
    private LabeledTextField passwordTextField;
    @FXML
    private Button editProfileButton;
    @FXML
    private Button loginButton;
    @FXML
    private Text errorText;

    @FXML
    private Tab globalRatingsTab;

    @FXML
    private ListView<Topic> topicsListView;

    @FXML
    private Text selectedTopicInfoText;

    @FXML
    private TabPane taskRatingsTabPane;

    private List<ProfileStatistic> profileStatistics;

    public GlobalProfilesController(
            FxmlService fxmlService,
            GlobalProfileService globalProfileService,
            GlobalCredentialsService globalCredentialsService,
            GlobalStatisticService globalStatisticService,
            LocalTopicService localTopicService,
            LocalTopicTasksService localTopicTasksService
    ) {
        super(fxmlService, globalProfileService);
        this.globalCredentialsService = globalCredentialsService;
        this.globalStatisticService = globalStatisticService;
        this.localTopicService = localTopicService;
        this.localTopicTasksService = localTopicTasksService;
    }

    @Override
    protected void onEdit(Profile profile, boolean isCreated, String title) {
        var credentials = (isCreated
                ? new Credentials(profile)
                : globalCredentialsService.findBy(profile)
        );

        // TODO check errors
        if (null == credentials) {
            return;
        }

        var changed = fxmlService.showInfoDialog(
                CredentialsInfoController.class,
                credentials,
                title, isCreated
        );

        if (changed) {
            globalCredentialsService.save(credentials);
            onSelect(profile);
        }
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        loginGridPane.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    protected void onSelect(Profile profile) {
        super.onSelect(profile);
        showSelectedProfile();
    }

    private void showSelectedProfile() {
        activitiesTabPane.getSelectionModel().select(globalProfileTab);

        var selectedProfile = profileService.getSelectedProfile();

        boolean selected = Optional.ofNullable(selectedProfile).isPresent();
        String profileText = (selected ? selectedProfile.getName() : "не выбран");
        loginInfoText.setText("Глобальный профиль: " + profileText);

        editProfileButton.setVisible(selected);

        globalRatingsTab.setDisable(!selected);
    }

    private void showRatings() {
        var topics = localTopicService.findAll();
        topicsListView.getItems().setAll(topics);

        topicsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        topicsListView.getSelectionModel().selectedItemProperty().addListener(
            (observableValue, oldTopic, newTopic) -> showRatingsFor(newTopic)
        );
    }

    private void showRatingsFor(Topic topic) {
        globalStatisticService.findBy(topic)
        .ifPresentOrElse(
            this::showRegisteredTopic,
            this::showNotRegisteredTopic
        );
    }

    private void showRegisteredTopic(TopicStatistic topicStatistic) {
        var topic = topicStatistic.getTopic();
        var topicTasks = localTopicTasksService.findBy(topic.getTopicType());

        selectedTopicInfoText.setText("");

        var tasks = new ArrayList<>(topicTasks.getTasks());
        tasks.add(topicTasks.getTest().getTask());

        List<Tab> tabs = tasks.stream()
                .map(task -> createRatingTab(topicStatistic, task, taskRatingsTabPane))
                .collect(Collectors.toList());

        taskRatingsTabPane.setDisable(false);
        taskRatingsTabPane.getTabs().setAll(tabs);
    }

    private Tab createRatingTab(TopicStatistic topicStatistic, Task task, TabPane tabPane) {
        Tab tab = new Tab(task.getName());

        var ratingListView = new RatingListView();
        tab.setContent(ratingListView);

        List<RatingInfo> ratingInfos = new ArrayList<>();
        for (ProfileStatistic profileStatistic : profileStatistics) {
            profileStatistic
                .getTopicStatisticFor(topicStatistic.getTopic())
                .flatMap(ts -> ts.getTaskStatisticFor(task))
                .map(ts -> new RatingInfo(
                    profileStatistic.getProfile().getName(),
                    ts.getResult()
                )).ifPresent(ratingInfos::add);
        }

        Collections.sort(ratingInfos);

        // TODO made as const
        if (ratingInfos.size() > 20) {
            ratingInfos = ratingInfos.subList(0, 20);
        }

        ratingListView.setItems(ratingInfos);

        var result = topicStatistic.getTaskStatisticFor(task)
                .map(TaskStatistic::getResult)
                .orElse(Result.NONE);

        var selfProfile =
                globalStatisticService.selectedProfileProperty().getValue();

        ratingListView.getItems()
                .add(0, new RatingInfo(selfProfile.getName(), result));

        ratingListView.setPrefSize(
                tabPane.getTabMaxWidth(),
                tabPane.getTabMaxHeight()
        );

        return tab;
    }

    private void showNotRegisteredTopic() {
        selectedTopicInfoText.setText("Неизвестная глобальная тема");

        taskRatingsTabPane.getTabs().clear();
        taskRatingsTabPane.setDisable(true);
    }

    @Override
    public void prepareToShow() {
        this.profileStatistics = globalStatisticService.findAll();

        showSelectedProfile();
        showRatings();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        editProfileButton.setOnAction(event -> onEdit(profileService.getSelectedProfile()));

        initLogin();
    }

    private void initLogin() {
        loginButton.setOnAction(event -> onLogin());
    }

    private void showLoginError() {
        errorText.setText("Некорректный логин/пароль");
    }

    private void onLogin() {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();

        Credentials credentials = globalCredentialsService.findBy(login);
        if (null == credentials) {
            showLoginError();
            return;
        }

        long loginPasswordHash = Credentials.calculatePasswordHash(password);
        if (credentials.getPasswordHash() != loginPasswordHash) {
            showLoginError();
            return;
        }

        loginTextField.setText("");
        passwordTextField.setText("");
        errorText.setText("");

        onSelect(credentials.getProfile());
    }
}
