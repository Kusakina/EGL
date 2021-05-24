package egl.client.controller.topic;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.controller.WindowController;
import egl.client.controller.profile.GlobalProfileController;
import egl.client.controller.profile.LocalProfilesController;
import egl.client.model.topic.LocalTopic;
import egl.client.model.topic.category.Category;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.GlobalProfileService;
import egl.client.service.model.profile.LocalProfileService;
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

    @FXML private Button localProfilesListButton;
    @FXML private Button globalProfileListButton;
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
        initializeLocalProfiles();
        initializeGlobalProfiles();
    }

    private void initializeLocalProfiles() {
        localProfilesListButton.setOnAction(event -> openLocalProfilesList());

        localProfilesListButton.textProperty().bindBidirectional(
                localProfileService.selectedProfileProperty(),
                new StringConverter<>() {
                    @Override
                    public String toString(Profile localProfile) {
                        if (null == localProfile) return "Выбрать локальный профиль";
                        return String.format("Локальный профиль: %s", localProfile.getName());
                    }

                    @Override
                    public Profile fromString(String s) {
                        return null;
                    }
                }
        );

        localProfileService.selectedProfileProperty().addListener((observableValue, oldProfile, newProfile) -> categoriesListView.refresh());
    }

    private void initializeGlobalProfiles() {
        globalProfileListButton.setOnAction(event -> openGlobalProfileController());

        globalProfileListButton.textProperty().bindBidirectional(
                globalProfileService.selectedProfileProperty(),
                new StringConverter<>() {
                    @Override
                    public String toString(Profile globalProfile) {
                        if (null == globalProfile) return "Выбрать глобальный профиль";
                        return String.format("Глобальный профиль: %s", globalProfile.getName());
                    }

                    @Override
                    public Profile fromString(String s) {
                        return null;
                    }
                }
        );

        globalProfileService.selectedProfileProperty().addListener((observableValue, oldProfile, newProfile) -> categoriesListView.refresh());
    }

    private void openLocalProfilesList() {
        var localProfilesRoot = fxmlService.load(LocalProfilesController.class);
        fxmlService.showStage(localProfilesRoot, "Локальные профили", WindowController.CLOSE);
    }

    private void openGlobalProfileController() {
        var globalProfileRoot = fxmlService.load(GlobalProfileController.class);
        fxmlService.showStage(globalProfileRoot, "Глобальный профиль", WindowController.CLOSE);
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
