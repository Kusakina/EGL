package egl.client.controller.topic;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.controller.ControllerUtils;
import egl.client.model.topic.LocalTopic;
import egl.client.service.CategoryService;
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
public class LocalTopicsListController extends Controller {

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
                    var controllerAndView = fxmlService.showStage(LocalTopicController.class, topic.getName());

                    var topicController = (LocalTopicController) controllerAndView.getController();
                    topicController.show(topic);
                }
        );
    }

    private void rescaleViews(double parentWidth, double parentHeight) {
        topicsListTableView.setPrefSize(parentWidth * 0.8, parentHeight * 0.8);
        ControllerUtils.rescaleTableView(topicsListTableView);
    }

    public void show() {
        show(stage.getWidth(), stage.getHeight());
    }

    public void show(double parentWidth, double parentHeight) {
        rescaleViews(parentWidth, parentHeight);
        showTopics();
    }

    private void showTopics() {
        var tableTopics = topicsListTableView.getItems();
        tableTopics.clear();

        var categories = categoryService.findAll();
        tableTopics.addAll(categories);
    }
}
