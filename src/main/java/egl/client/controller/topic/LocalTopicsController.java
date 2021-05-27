package egl.client.controller.topic;

import egl.client.controller.Controller;
import egl.client.controller.WindowController;
import egl.client.controller.profile.GlobalProfilesController;
import egl.client.controller.profile.LocalProfilesController;
import egl.client.model.topic.LocalTopic;
import egl.client.model.topic.category.Category;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.GlobalProfileService;
import egl.client.service.model.profile.LocalProfileService;
import egl.client.service.model.profile.ProfileService;
import egl.client.service.model.statistic.LocalStatisticService;
import egl.client.service.model.topic.CategoryService;
import egl.client.view.table.column.ButtonColumn;
import egl.client.view.table.list.InfoSelectEditRemoveListView;
import egl.core.model.profile.Profile;
import egl.core.model.statistic.TaskStatistic;
import egl.core.model.statistic.TopicStatistic;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
@RequiredArgsConstructor
public class LocalTopicsController implements Controller {

    private final FxmlService fxmlService;
    private final CategoryService categoryService;
    private final LocalProfileService localProfileService;
    private final LocalStatisticService localStatisticService;
    private final GlobalProfileService globalProfileService;

    @FXML private InfoSelectEditRemoveListView<Category> categoriesListView;
    @FXML private ButtonColumn<Category> copyCategoryColumn;
    @FXML private TableColumn<Category, String> topicStatisticColumn;

    @FXML private Button selectLocalProfileButton;
    @FXML private Button selectGlobalProfileButton;
    @FXML private Button createCategoryButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCategories();
        initializeProfiles();
    }

    private void initializeCategories() {
        categoriesListView.setService(categoryService);
        categoriesListView.setOnSelect(this::onTopicSelect);
        categoriesListView.setOnEdit(this::onCategoryEdit);

        copyCategoryColumn.setOnAction(this::onCategoryCopy);
        createCategoryButton.setOnAction(event -> onCategoryCreate());

        topicStatisticColumn.setCellValueFactory(param -> {
            var category = param.getValue();
            var statisticString = getCategoryStatistic(category);
            return new SimpleStringProperty(statisticString);
        });
    }

    private String getCategoryStatistic(Category category) {
        var profile = localProfileService.getSelectedProfile();
        if (null == profile) return "Нет данных";

        TopicStatistic topicStatistic = localStatisticService.findBy(profile, category);

        var passedTasksCount = topicStatistic.getTaskStatistics().stream()
                .map(TaskStatistic::getResult)
                .filter(result -> result.getCorrectAnswers() > 0)
                .count();

        return String.format("Количество пройденных заданий: %d", passedTasksCount);
    }

    private void initializeProfiles() {
        initSelectProfileButton(selectLocalProfileButton, localProfileService, LocalProfilesController.class, "Локальный");
        initSelectProfileButton(selectGlobalProfileButton, globalProfileService, GlobalProfilesController.class, "Глобальный");
    }

    private <ProfileType extends Profile> void initSelectProfileButton(
            Button selectProfileButton,
            ProfileService<ProfileType> profileService,
            Class<? extends Controller> selectProfileControllerClass,
            String profileTypeName) {
        String profileText = String.format("%s профиль", profileTypeName);

        selectProfileButton.setOnAction(event -> {
            var selectProfileRoot = fxmlService.load(selectProfileControllerClass);
            fxmlService.showStage(selectProfileRoot, profileText, WindowController.CLOSE);
        });

        selectProfileButton.textProperty().bindBidirectional(
                profileService.selectedProfileProperty(),
                new StringConverter<>() {
                    @Override
                    public String toString(ProfileType localProfile) {
                        if (null == localProfile) return String.format("Выбрать %s", profileText.toLowerCase());
                        return String.format("%s профиль: %s", profileText, localProfile.getName());
                    }

                    @Override
                    public ProfileType fromString(String s) {
                        return null;
                    }
                }
        );

        profileService.selectedProfileProperty().addListener((observableValue, oldProfile, newProfile) -> categoriesListView.refresh());
    }

    private void onTopicSelect(LocalTopic topic) {
        var localTopicRoot = fxmlService.load(TopicTasksController.class);

        var topicController = localTopicRoot.getController();
        topicController.setContext(topic);

        fxmlService.showStage(
                localTopicRoot, topic.getName(), WindowController.CLOSE
        );
    }

    private void onCategoryEdit(Category category) {
        onCategoryEdit(category, false, "Изменить данные");
    }

    private void onCategoryCreate() {
        onCategoryCopy(new Category());
    }

    private void onCategoryCopy(Category category) {
        onCategoryEdit(new Category(category), true, "Новая категория");
    }

    private void onCategoryEdit(Category category, boolean isCreated, String title) {
        var changed = fxmlService.showInfoDialog(
                CategoryInfoController.class,
                category,
                title, isCreated
        );

        if (changed) {
            categoryService.save(category);
            categoriesListView.showItems();
        }
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        categoriesListView.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    public void prepareToShow() {
        showCategories();
    }

    private void showCategories() {
        categoriesListView.showItems();
    }

    @Override
    public void prepareToClose() {

    }
}
