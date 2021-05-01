package egl.client.controller.task.category;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.ControllerUtils;
import egl.client.controller.task.TheoryTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.client.model.topic.category.Word;
import egl.core.model.task.Task;
import egl.core.model.topic.Topic;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class CategoryTheoryTaskController extends TheoryTaskController {

    @FXML private TableView<Translation> translationsTableView;
    @FXML private TableColumn<Translation, Word> sourceTableColumn;
    @FXML private TableColumn<Translation, Word> targetTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        sourceTableColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        targetTableColumn.setCellValueFactory(new PropertyValueFactory<>("target"));
    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {
        descriptionTextArea.setPrefSize(parentWidth, parentHeight * 0.3);
        translationsTableView.setPrefSize(parentWidth, parentHeight * 0.6);
        ControllerUtils.rescaleTableView(translationsTableView);
    }

    @Override
    protected void prepareToStart(Task controllerTask, Topic controllerTopic) {
        super.prepareToStart(controllerTask, controllerTopic);

        Category category = (Category) controllerTopic;
        var categoryTranslations = category.getTranslations();

        var tableTranslations = translationsTableView.getItems();
        tableTranslations.clear();
        tableTranslations.addAll(categoryTranslations);
    }
}
