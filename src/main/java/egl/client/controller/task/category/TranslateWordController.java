package egl.client.controller.task.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import egl.client.controller.task.LocalTaskController;
import egl.client.model.local.topic.category.Category;
import egl.client.model.local.topic.category.Translation;
import egl.client.service.model.topic.CategoryService;
import egl.client.service.model.topic.LocalTopicInfoService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class TranslateWordController extends LocalTaskController<Category> {

    private static final int MAX_QUESTIONS_COUNT = 8;
    private List<TranslateWordView> questionViews;
    private List<Translation> translations;

    @FXML
    private VBox tasksVBox;

    public TranslateWordController(LocalTopicInfoService localTopicInfoService,
                                   CategoryService specificLocalTopicInfoService) {
        super(localTopicInfoService, specificLocalTopicInfoService);
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
        Collections.shuffle(translations);

        final int tasksCount = Math.min(MAX_QUESTIONS_COUNT, translations.size());
        this.questionViews = new ArrayList<>();
        for (int i = 0; i < tasksCount; ++i) {
            var questionView = createTestQuestion(i);
            tasksVBox.getChildren().add(questionView);
            questionViews.add(questionView);
        }
    }

    private TranslateWordView createTestQuestion(int index) {
        int SourceOrTarget = (int) (Math.random() * 2);
        return new TranslateWordView(translations.get(index), SourceOrTarget, index);
    }

    @Override
    protected void prepareToFinish() {
        for (var questionView : questionViews) {
            result.registerAnswer(questionView.isCorrect());
        }
    }
}
