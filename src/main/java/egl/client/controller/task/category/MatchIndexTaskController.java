package egl.client.controller.task.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import egl.client.controller.task.LocalTaskController;
import egl.client.model.local.topic.category.Category;
import egl.client.service.model.topic.CategoryService;
import egl.client.service.model.topic.LocalTopicInfoService;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class MatchIndexTaskController extends LocalTaskController<Category> {

    @FXML
    private GridPane tasksGridPane;

    @Value("${task.match_index.max_tasks_count}")
    private int maxTasksCount;

    private List<InputIndexView> questionViews;
    private List<FixedIndexView> answerViews;

    public MatchIndexTaskController(LocalTopicInfoService localTopicInfoService, CategoryService categoryService) {
        super(localTopicInfoService, categoryService);
    }

    private void prepareTasks() {
        Category category = specificLocalTopic;

        var translations = category.getTranslations();

        final int tasksCount = Math.min(maxTasksCount, translations.size());

        this.questionViews = new ArrayList<>();
        Collections.shuffle(translations);
        for (int i = 0; i < tasksCount; ++i) {
            var questionView = new InputIndexView(translations.get(i), tasksCount);
            tasksGridPane.add(questionView, 0, i);
            questionViews.add(questionView);
        }

        this.answerViews = new ArrayList<>();
        Collections.shuffle(translations);
        for (int i = 0; i < tasksCount; ++i) {
            var answerView = new FixedIndexView(translations.get(i), i);
            tasksGridPane.add(answerView, 1, i);
            answerViews.add(answerView);
        }
    }

    @Override
    protected void prepareToStart() {
        prepareTasks();
    }

    @Override
    protected void prepareToFinish() {

    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {

    }
}
