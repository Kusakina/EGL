package egl.client.controller.profile;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import egl.client.controller.profile.info.RatingListView;
import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.RatingInfo;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.global.GlobalCredentialsService;
import egl.client.service.model.global.GlobalProfileService;
import egl.client.service.model.global.GlobalStatisticServiceHolder;
import egl.client.service.model.local.LocalTopicService;
import egl.client.service.model.local.LocalTopicTasksService;
import egl.client.view.text.LabeledTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private final GlobalStatisticServiceHolder globalStatisticService;
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

    public GlobalProfilesController(FxmlService fxmlService,
                                    GlobalProfileService profileService,
                                    GlobalCredentialsService globalCredentialsService,
                                    GlobalStatisticServiceHolder globalStatisticService,
                                    LocalTopicService localTopicService,
                                    LocalTopicTasksService localTopicTasksService) {
        super(fxmlService, profileService);
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
        prepareToShow();
    }

    private void showSelectedProfile() {
        activitiesTabPane.getSelectionModel().select(globalProfileTab);

        var selectedProfileName = profileService.getSelectedProfile().map(Profile::getName);

        String profileText = selectedProfileName.orElse("не выбран");
        loginInfoText.setText("Глобальный профиль: " + profileText);

        editProfileButton.setVisible(selectedProfileName.isPresent());
        globalRatingsTab.setDisable(selectedProfileName.isEmpty());
    }

    private void showRatings() {
        try {
            profileStatistics = globalStatisticService.findAll();
        } catch (EntityServiceException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Проблема при загрузке глобальных результатов",
                    ButtonType.OK).show();
        }

        topicsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        topicsListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTopic, newTopic) -> showRatingsFor(newTopic)
        );

        try {
            var topics = localTopicService.findAll();
            topicsListView.getItems().setAll(topics);
        } catch (EntityServiceException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Проблема при загрузке списка локальных топиков",
                    ButtonType.OK).show();
        }
    }

    private void showRatingsFor(Topic topic) {
        if (null == topic) {
            return;
        }

        try {
            globalStatisticService.findBy(topic)
                    .ifPresentOrElse(
                            this::showRegisteredTopic,
                            this::showNotRegisteredTopic
                    );
        } catch (EntityServiceException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Проблема при загрузке результатов для темы",
                    ButtonType.OK).show();
        }
    }

    private void showRegisteredTopic(TopicStatistic topicStatistic) {
        var globalTopic = topicStatistic.getTopic();
        var topicTasks = localTopicTasksService.findBy(globalTopic.getTopicType());

        selectedTopicInfoText.setText("");

        var tasks = new ArrayList<>(topicTasks.getTasks());
        tasks.add(topicTasks.getTest().getTask());

        List<Tab> tabs = tasks.stream()
                .map(task -> createRatingTab(globalTopic, task, taskRatingsTabPane))
                .collect(Collectors.toList());

        taskRatingsTabPane.setDisable(false);
        taskRatingsTabPane.getTabs().setAll(tabs);
    }

    private Tab createRatingTab(Topic globalTopic, Task task, TabPane tabPane) {
        Tab tab = new Tab(task.getName());

        var ratingListView = new RatingListView();
        tab.setContent(ratingListView);

        List<RatingInfo> ratingInfos = profileStatistics.stream()
                .map(profileStatistic ->
                    globalStatisticService.tryFindBy(
                        profileStatistic,
                        globalTopic,
                        task
                    )
                ).filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .map(RatingInfo::new)
                .filter(RatingInfo::hasScores)
                .sorted()
                .limit(20) // TODO made as const
                .collect(Collectors.toUnmodifiableList());

        ratingListView.setItems(ratingInfos);
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
        showSelectedProfile();
        showRatings();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        editProfileButton.setOnAction(event ->
                profileService.getSelectedProfile().ifPresent(this::onEdit)
        );

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

        try {
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
        } catch (EntityServiceException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Проблема при проверке логина/пароля",
                    ButtonType.OK).show();
        }
    }
}
