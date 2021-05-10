package egl.client.controller.topic;

import egl.client.controller.Controller;
import egl.client.controller.WindowController;
import egl.client.model.topic.LocalTopic;
import egl.client.model.topic.category.Category;
import egl.client.service.FxmlService;
import egl.client.service.model.topic.CategoryService;
import egl.client.view.table.EntityServiceListView;
import javafx.fxml.FXML;
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

    @FXML private EntityServiceListView<Category> categoriesListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCategories();
    }

    private void initializeCategories() {
        categoriesListView.setService(categoryService);
        categoriesListView.setOnSelect(this::onSelect);
    }

    public void onSelect(LocalTopic topic) {
        var localTopicRoot = fxmlService.load(TopicTasksController.class);

        var topicController = (TopicTasksController) localTopicRoot.getController();
        topicController.setContext(topic);

        fxmlService.showStage(
                localTopicRoot, topic.getName(), WindowController.CLOSE
        );
    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {
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
