package egl.client.controller.task.category;

import egl.client.controller.task.TheoryTaskController;
import egl.client.controller.topic.info.TranslationsListView;
import egl.client.model.local.topic.category.Category;
import egl.client.service.model.local.CategoryService;
import egl.client.service.model.local.LocalTopicInfoService;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class CategoryTheoryTaskController extends TheoryTaskController<Category> {

    @FXML private TranslationsListView translationsListView;

    public CategoryTheoryTaskController(LocalTopicInfoService localTopicInfoService, CategoryService categoryService) {
        super(localTopicInfoService, categoryService);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        descriptionTextArea.setPrefSize(parentWidth, parentHeight * 0.3);
        translationsListView.setPrefSize(parentWidth, parentHeight * 0.6);
    }

    @Override
    protected void prepareToStart() {
        super.prepareToStart();
        translationsListView.initData(specificLocalTopic, false);
    }
}
