package egl.client.controller.task.category;

import egl.client.controller.task.TheoryTaskController;
import egl.client.controller.topic.info.TranslationsListView;
import egl.client.model.topic.category.Category;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
public class CategoryTheoryTaskController extends TheoryTaskController {

    @FXML private TranslationsListView translationsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        descriptionTextArea.setPrefSize(parentWidth, parentHeight * 0.3);
        translationsListView.setPrefSize(parentWidth, parentHeight * 0.6);
    }

    @Override
    protected void prepareSpecificTheory() {
        Category category = (Category) controllerTopic;
        translationsListView.initData(category, false);
    }
}
