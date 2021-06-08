package egl.client.controller.task.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import egl.client.controller.task.LocalTaskController;
import egl.client.model.local.topic.category.Category;
import egl.client.model.local.topic.category.Translation;
import egl.client.service.model.topic.CategoryService;
import egl.client.service.model.topic.LocalTopicInfoService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class TrueFalseTaskController extends LocalTaskController<Category> {

    @FXML
    private VBox tasksVBox;

    @Value("${task.true_false.max_tasks_count}")
    private int maxTasksCount;

    private final Random random;

    private List<TrueFalseQuestionView> questionViews;
    private List<Translation> translations;
    private List<Translation> answers;

    public TrueFalseTaskController(LocalTopicInfoService localTopicInfoService, CategoryService categoryService) {
        super(localTopicInfoService, categoryService);

        this.random = new Random();
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        for (var questionView : questionViews) {
            questionView.setPrefWidth(parentWidth);
        }
    }

    @Override
    protected void prepareToStart() {
        Category category = specificLocalTopic;
        this.translations = category.getTranslations();
        this.answers = category.getTranslations();
        Collections.shuffle(translations, random);
        Collections.shuffle(answers, random);

        final int tasksCount = Math.min(maxTasksCount, translations.size());
        this.questionViews = new ArrayList<>();
        for (int i = 0; i < tasksCount; ++i) {
            var questionView = createTestQuestion(i);
            tasksVBox.getChildren().add(questionView);
            questionViews.add(questionView);
        }
    }

    private TrueFalseQuestionView createTestQuestion(int index) {
        var isCorrect = random.nextBoolean();
        if (isCorrect) return new TrueFalseQuestionView(translations.get(index), translations.get(index), index);
        else return new TrueFalseQuestionView(translations.get(index), answers.get(index), index);
    }

    @Override
    protected void prepareToFinish() {
        for (var questionView : questionViews) {
            result.registerAnswer(questionView.isCorrect());
        }
    }
}
