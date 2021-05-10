package egl.client.controller.topic;

import egl.client.controller.Controller;
import egl.client.controller.WindowController;
import egl.client.controller.profile.LocalProfilesController;
import egl.client.model.profile.LocalProfile;
import egl.client.model.topic.LocalTopic;
import egl.client.model.topic.category.Category;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.LocalProfileService;
import egl.client.service.model.topic.CategoryService;
import egl.client.view.table.EntityServiceListView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML private EntityServiceListView<Category> categoriesListView;
    @FXML private Button localProfilesListButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCategories();
        initializeProfiles();
    }

    private void initializeCategories() {
        categoriesListView.setService(categoryService);
        categoriesListView.setOnSelect(this::onTopicSelect);
    }

    private void initializeProfiles() {
        localProfilesListButton.setOnAction(event -> openProfilesList());

        localProfilesListButton.textProperty().bindBidirectional(
                localProfileService.selectedProfileProperty(),
                new StringConverter<>() {
                    @Override
                    public String toString(LocalProfile localProfile) {
                        if (null == localProfile) return "Выбрать локальный профиль";
                        return String.format("Локальный профиль: %s", localProfile.getName());
                    }

                    @Override
                    public LocalProfile fromString(String s) {
                        return null;
                    }
                }
        );
    }

    private void openProfilesList() {
        var profilesRoot = fxmlService.load(LocalProfilesController.class);
        fxmlService.showStage(profilesRoot, "Локальные профили", WindowController.CLOSE);
    }

    private void onTopicSelect(LocalTopic topic) {
        var localTopicRoot = fxmlService.load(TopicTasksController.class);

        var topicController = (TopicTasksController) localTopicRoot.getController();
        topicController.setContext(topic);

        fxmlService.showStage(
                localTopicRoot, topic.getName(), WindowController.CLOSE
        );
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