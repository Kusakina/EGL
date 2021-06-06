package egl.client.controller.topic;

import egl.client.controller.Controller;
import egl.client.controller.WindowController;
import egl.client.controller.profile.GlobalProfilesController;
import egl.client.controller.profile.LocalProfilesController;
import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.model.local.topic.category.Category;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityService;
import egl.client.service.model.profile.GlobalProfileService;
import egl.client.service.model.profile.LocalProfileService;
import egl.client.service.model.profile.ProfileService;
import egl.client.service.model.statistic.LocalStatisticService;
import egl.client.service.model.topic.CategoryService;
import egl.client.service.model.topic.LocalTopicInfoService;
import egl.client.service.model.topic.LocalTopicService;
import egl.client.view.table.column.ButtonColumn;
import egl.client.view.table.list.InfoSelectEditRemoveListView;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

@Component
@FxmlView
@RequiredArgsConstructor
public class LocalTopicsController implements Controller {

    private final FxmlService fxmlService;
    private final LocalTopicService localTopicService;
    private final LocalTopicInfoService localTopicInfoService;
    private final CategoryService categoryService;
    private final LocalProfileService localProfileService;
    private final LocalStatisticService localStatisticService;
    private final GlobalProfileService globalProfileService;

    @FXML private InfoSelectEditRemoveListView<Topic> categoriesListView;
    @FXML private ButtonColumn<Topic> copyCategoryColumn;
    @FXML private TableColumn<Topic, String> topicStatisticColumn;

    @FXML private Button selectLocalProfileButton;
    @FXML private Button selectGlobalProfileButton;
    @FXML private Button createCategoryButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCategories();
        initializeProfiles();
    }


    @RequiredArgsConstructor
    private class TypedTopicService implements EntityService<Topic> {

        private final TopicType topicType;

        @Override
        public List<Topic> findAll() {
            return localTopicService.findAllBy(topicType);
        }

        @Override
        public void save(Topic entity) {
            localTopicService.save(entity);
        }

        @Override
        public void remove(Topic entity) {
            localTopicService.remove(entity);
        }
    }

    private Consumer<Topic> processCategory(Consumer<Category> categoryConsumer) {
        Consumer<LocalTopicInfo> localTopicInfoConsumer = (LocalTopicInfo localTopicInfo) -> {
              Category category = categoryService.findBy(localTopicInfo);
              categoryConsumer.accept(category);
        };

        return processLocalTopic(localTopicInfoConsumer);
    }

    private Consumer<Topic> processLocalTopic(Consumer<LocalTopicInfo> localTopicInfoConsumer) {
        return (Topic topic) -> {
            LocalTopicInfo localTopicInfo = localTopicInfoService.findBy(topic);
            localTopicInfoConsumer.accept(localTopicInfo);
        };
    }

    private void initializeCategories() {
        categoriesListView.setService(new TypedTopicService(TopicType.CATEGORY));
        categoriesListView.setOnSelect(this::onTopicSelect);
        categoriesListView.setOnEdit(processCategory(this::onCategoryEdit));

        copyCategoryColumn.setOnAction(processCategory(this::onCategoryCopy));
        createCategoryButton.setOnAction(event -> onCategoryCreate());

        topicStatisticColumn.setCellValueFactory(param -> {
            var topic = param.getValue();
            var statisticString = getTopicStatistic(topic);
            return new SimpleStringProperty(statisticString);
        });
    }

    private String getTopicStatistic(Topic topic) {
        var profile = localProfileService.getSelectedProfile();
        if (null == profile) return "Нет данных";

        TopicStatistic topicStatistic = localStatisticService.findBy(profile, topic);

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

    private void initSelectProfileButton(
            Button selectProfileButton,
            ProfileService profileService,
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
                    public String toString(Profile profile) {
                        if (null == profile) return String.format("Выбрать %s", profileText.toLowerCase());
                        return String.format("%s профиль: %s", profileText, profile.getName());
                    }

                    @Override
                    public Profile fromString(String s) {
                        return null;
                    }
                }
        );

        profileService.selectedProfileProperty().addListener((observableValue, oldProfile, newProfile) -> categoriesListView.refresh());
    }

    private void onTopicSelect(Topic topic) {
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
