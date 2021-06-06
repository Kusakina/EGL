package egl.client.controller.task.category;

import egl.client.controller.task.LocalTaskController;
import egl.client.model.local.topic.category.Category;
import egl.client.model.local.topic.category.Translation;
import egl.client.service.model.topic.CategoryService;
import egl.client.service.model.topic.LocalTopicInfoService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.client.model.topic.category.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@FxmlView
public class TrueFalseTaskController extends LocalTaskController<Category> {

    private static final int MAX_QUESTIONS_COUNT = 8;
    private List<TrueFalseQuestionView> questionViews;
    private List<Translation> translations;
    private List<Translation> answers;

    @FXML
    private VBox tasksVBox;

    public TrueFalseTaskController(LocalTopicInfoService localTopicInfoService, CategoryService categoryService) {
        super(localTopicInfoService, categoryService);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {

    }

    @Override
    protected void prepareToStart() {
        Category category = specificLocalTopic;
        this.translations = category.getTranslations();
        this.answers = category.getTranslations();
        Collections.shuffle(translations);
        Collections.shuffle(answers);

        final int tasksCount = Math.min(MAX_QUESTIONS_COUNT, translations.size());
        this.questionViews = new ArrayList<>();
        for (int i = 0; i < tasksCount; ++i) {
            var questionView = createTestQuestion(i);
            tasksVBox.getChildren().add(questionView);
            questionViews.add(questionView);
        }
    }
    private TrueFalseQuestionView createTestQuestion(int index) {
        int bool = (int) (Math.random() * 2);
        if (bool == 0) return new TrueFalseQuestionView(translations.get(index), translations.get(index), index);
        else return new TrueFalseQuestionView(translations.get(index), answers.get(index), index);
    }

    @Override
    protected void prepareToFinish() {

    }
}
