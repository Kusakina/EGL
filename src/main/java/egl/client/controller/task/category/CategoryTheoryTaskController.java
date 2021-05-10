package egl.client.controller.task.category;

import egl.client.controller.task.TheoryTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.client.view.table.CustomTableView;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
public class CategoryTheoryTaskController extends TheoryTaskController {

    @FXML private CustomTableView<Translation> translationsTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {
        descriptionTextArea.setPrefSize(parentWidth, parentHeight * 0.3);
        translationsTableView.setPrefSize(parentWidth, parentHeight * 0.6);
    }

    @Override
    protected void prepareSpecificTheory() {
        Category category = (Category) controllerTopic;
        var categoryTranslations = category.getTranslations();
        translationsTableView.showItems(categoryTranslations);
    }
}
