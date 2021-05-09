package egl.client.controller.topic;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.controller.ControllerUtils;
import egl.client.controller.WindowController;
import egl.client.model.topic.LocalTopic;
import egl.client.service.model.CategoryService;
import egl.client.service.FxmlService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class LocalTopicsListController implements Controller {

    private final FxmlService fxmlService;
    private final CategoryService categoryService;

    @FXML private TableView<LocalTopic> topicsListTableView;
    @FXML private TableColumn<LocalTopic, String> topicNameColumn;
    @FXML private TableColumn<LocalTopic, Void> topicLearnColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
    }

    private void initializeColumns() {
        ControllerUtils.initializePropertyColumn(topicNameColumn, "name");

        ControllerUtils.initializeButtonColumn(
                topicLearnColumn,
                "Изучить",
                (event, topic) -> {
                    var localTopicRoot = fxmlService.load(LocalTopicController.class);

                    var topicController = (LocalTopicController) localTopicRoot.getController();
                    topicController.setContext(topic);

                    fxmlService.showStage(
                            localTopicRoot, topic.getName(), WindowController.CLOSE
                    );
                }
        );
    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {
        topicsListTableView.setPrefSize(parentWidth, parentHeight);
        ControllerUtils.rescaleTableView(topicsListTableView);
    }

    @Override
    public void prepareToShow() {
        var tableTopics = topicsListTableView.getItems();
        tableTopics.clear();

        var categories = categoryService.findAll();
        tableTopics.addAll(categories);
    }

    @Override
    public void prepareToClose() {

    }
}
