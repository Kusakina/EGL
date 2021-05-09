package egl.client.controller.topic;

import egl.client.controller.Controller;
import egl.client.controller.WindowController;
import egl.client.model.topic.LocalTopic;
import egl.client.model.topic.category.Category;
import egl.client.service.FxmlService;
import egl.client.service.model.topic.CategoryService;
import egl.client.view.table.topic.LocalTopicsListView;
import javafx.fxml.FXML;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
@RequiredArgsConstructor
public class ProfileTopicsController implements Controller {

    private final FxmlService fxmlService;
    private final CategoryService categoryService;

    @FXML private LocalTopicsListView<Category> categoriesListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCategories();
    }

    private void initializeCategories() {
        categoriesListView.setOnSelect(this::onSelect);
        categoriesListView.setOnRemove(this::onCategoryRemove);
    }

    public void onSelect(LocalTopic topic) {
        var localTopicRoot = fxmlService.load(TopicTasksController.class);

        var topicController = (TopicTasksController) localTopicRoot.getController();
        topicController.setContext(topic);

        fxmlService.showStage(
                localTopicRoot, topic.getName(), WindowController.CLOSE
        );
    }

    public void onCategoryRemove(Category category) {
        categoryService.remove(category);
    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {
        categoriesListView.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    public void prepareToShow() {
        var categories = categoryService.findAll();
        categoriesListView.showItems(categories);
    }

    @Override
    public void prepareToClose() {

    }
}
