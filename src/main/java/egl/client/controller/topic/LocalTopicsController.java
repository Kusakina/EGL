package egl.client.controller.topic;

import egl.client.controller.Controller;
import egl.client.controller.WindowController;
import egl.client.controller.profile.StatisticsController;
import egl.client.controller.profile.global.GlobalStatisticsController;
import egl.client.controller.profile.local.LocalStatisticsController;
import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.model.core.topic.TopicType;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.model.local.topic.category.Category;
import egl.client.service.ErrorService;
import egl.client.service.FileService;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityService;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.AbstractStatisticService;
import egl.client.service.model.core.StatisticService;
import egl.client.service.model.core.TopicByLocalService;
import egl.client.service.model.global.GlobalStatisticService;
import egl.client.service.model.global.GlobalTopicService;
import egl.client.service.model.local.*;
import egl.client.view.table.column.ButtonColumn;
import egl.client.view.table.list.InfoSelectEditRemoveListView;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@FxmlView
@RequiredArgsConstructor
public class LocalTopicsController implements Controller {

    private final FxmlService fxmlService;
    private final LocalTopicService localTopicService;
    private final LocalTopicInfoService localTopicInfoService;
    private final LocalTopicTasksService localTopicTasksService;
    private final CategoryService categoryService;
    private final LocalStatisticService localStatisticService;
    private final GlobalTopicService globalTopicService;
    private final GlobalStatisticService globalStatisticService;

    @FXML private InfoSelectEditRemoveListView<Topic> categoriesListView;
    @FXML private TableColumn<Topic, String> topicLocalStatisticColumn;
    @FXML private TableColumn<Topic, String> topicGlobalStatisticColumn;
    @FXML private ButtonColumn<Topic> copyCategoryColumn;
    @FXML private ButtonColumn<Topic> registerCategoryColumn;
    @FXML private ButtonColumn<Topic> saveCategoryColumn;

    @FXML private Button selectLocalProfileButton;
    @FXML private Button selectGlobalProfileButton;
    @FXML private Button createCategoryButton;
    @FXML private Button loadCategoriesButton;

    private Stage stage;

    public void setContext(Stage stage) {
        this.stage = stage;
    }

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
        public Topic save(Topic entity) {
            return localTopicService.save(entity);
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
        categoriesListView.setOnRemove(processCategory(this::onCategoryRemove));

        createCategoryButton.setOnAction(event -> onCategoryCreate());
        copyCategoryColumn.setOnAction(processCategory(this::onCategoryCopy));
        registerCategoryColumn.setOnAction(processLocalTopic(this::onLocalTopicRegister));

        saveCategoryColumn.setOnAction(processCategory(this::onCategorySave));
        loadCategoriesButton.setOnAction(event -> onCategoriesLoad());

        initializeStatisticColumn(topicLocalStatisticColumn, localStatisticService, localTopicService);
        initializeStatisticColumn(topicGlobalStatisticColumn, globalStatisticService, globalTopicService);
    }

    private void initializeStatisticColumn(
            TableColumn<Topic, String> topicStatisticColumn,
            StatisticService statisticService,
            TopicByLocalService topicByLocalService
    ) {
        topicStatisticColumn.setCellValueFactory(param -> {
            var topic = param.getValue();
            var statisticString = getTopicStatistic(statisticService, topicByLocalService, topic);
            return new SimpleStringProperty(statisticString);
        });
    }

    private String getTopicStatistic(StatisticService statisticService,
                                     TopicByLocalService topicByLocalService,
                                     Topic localTopic) {
        try {
            var topicOptional = topicByLocalService.findTopicByLocal(localTopic);
            if (topicOptional.isEmpty()) {
                return TopicByLocalService.TOPIC_NOT_REGISTERED;
            }

            return topicOptional.flatMap(statisticService::findBy)
                    .map(topicStatistic -> {
                        var tasks = localTopicTasksService.findBy(localTopic.getTopicType()).getTasks();

                        Function<TaskStatistic, Result> resultGenerator = TaskStatistic::getResult;

                        var passedTasksCount = tasks.stream()
                                .map(task -> statisticService.findBy(topicStatistic, task))
                                .map(resultGenerator)
                                .filter(result -> result.getCorrectAnswers() > 0)
                                .count();

                        return String.format("%d из %d", passedTasksCount, tasks.size());
                    }).orElse(AbstractStatisticService.NO_DATA);
        } catch (EntityServiceException e) {
            return AbstractStatisticService.NO_DATA;
        }
    }

    private void initializeProfiles() {
        initSelectProfileButton(selectLocalProfileButton, localStatisticService, LocalStatisticsController.class, "Локальный");
        initSelectProfileButton(selectGlobalProfileButton, globalStatisticService, GlobalStatisticsController.class, "Глобальный");
    }

    private void initSelectProfileButton(
            Button selectProfileButton,
            StatisticService statisticService,
            Class<? extends StatisticsController> statisticsControllerClass,
            String profileTypeName) {
        String profileText = String.format("%s профиль", profileTypeName);

        selectProfileButton.setOnAction(event -> {
            var selectProfileRoot = fxmlService.load(statisticsControllerClass);
            fxmlService.showController(selectProfileRoot, profileText, WindowController.CLOSE);
        });

        selectProfileButton.textProperty().bindBidirectional(
                statisticService.selectedProfileProperty(),
                new StringConverter<>() {
                    @Override
                    public String toString(Profile profile) {
                        if (null == profile) return String.format("Выбрать %s", profileText.toLowerCase());
                        return String.format("%s: %s", profileText, profile.getName());
                    }

                    @Override
                    public Profile fromString(String s) {
                        return null;
                    }
                }
        );

        statisticService.selectedProfileProperty().addListener((observableValue, oldProfile, newProfile) -> categoriesListView.refresh());
    }

    private void onTopicSelect(Topic topic) {
        var localTopicRoot = fxmlService.load(TopicTasksController.class);

        var topicController = localTopicRoot.getController();
        topicController.setContext(topic);

        fxmlService.showController(
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
            saveCategory(category);
            categoriesListView.showItems();
        }
    }

    private void saveCategory(Category category) {
        categoryService.save(category);
        try {
            globalTopicService.initializeRegistration(category.getLocalTopicInfo());
        } catch (EntityServiceException ignored) {

        }
    }

    private void onCategoryRemove(Category category) {
        var topic = category.getLocalTopicInfo().getTopic();

        localStatisticService.removeAllBy(topic);
        categoryService.remove(category);

        categoriesListView.removeItem(topic);
    }

    private void onLocalTopicRegister(LocalTopicInfo localTopicInfo) {
        try {
            globalTopicService.registerTopic(localTopicInfo);
            refresh();
        } catch (EntityServiceException e) {
            ErrorService.showErrorAlert("Категория не зарегистрирована из-за проблем с подключением к глобальной базе данных");
        }
    }

    private void onCategorySave(Category category) {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить категорию");
        var file = fileChooser.showSaveDialog(stage);
        if (null != file) {
            FileService.save(category, file);
        }
    }

    private void onCategoriesLoad() {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить категории");
        var files = fileChooser.showOpenMultipleDialog(stage);
        if (null == files) return;

        files.forEach(file -> {
            try {
                var category = FileService.loadCategory(file);
                saveCategory(category);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        categoriesListView.showItems();
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        categoriesListView.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    public void prepareToShow() {
        showCategories();

        try {
            globalTopicService.initializeRegistrations(
                    categoriesListView.getItems()
            );
        } catch (EntityServiceException ignored) {

        }

        refresh();
    }

    private void showCategories() {
        categoriesListView.showItems();
    }

    @Override
    public void prepareToClose() {

    }

    @Override
    public void refresh() {
        categoriesListView.refresh();
    }
}
