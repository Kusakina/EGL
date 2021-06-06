package egl.client.controller.task.category;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@FxmlView
public class TranslateWordController extends AbstractTaskController {

    private static final int MAX_QUESTIONS_COUNT = 8;
    private List<TranslateWordView> questionViews;
    private List<Translation> translations;

    @FXML
    private VBox tasksVBox;

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {

    }

    @Override
    protected void prepareToStart() {
        Category category = (Category) controllerTopic;
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

    }
}
