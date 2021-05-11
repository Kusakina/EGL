package egl.client.controller.task.category;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.client.model.topic.category.Word;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class OneOfFourTaskController extends AbstractTaskController {

    private static final int MAX_TASKS_COUNT = 8;
    private static final int MAX_ANSWERS_COUNT = 4;

    @FXML
    private VBox tasksVBox;

    private List<OneOfFourQuestionView> questionViews;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private List<Translation> translations;

    private void prepareTasks() {
        Category category = (Category) controllerTopic;

        this.translations = category.getTranslations();
        Collections.shuffle(translations);

        final int tasksCount = Math.min(MAX_TASKS_COUNT, translations.size());
        final int answersCount = Math.min(MAX_ANSWERS_COUNT, translations.size());

        this.questionViews = new ArrayList<>();
        for (int i = 0; i < tasksCount; ++i) {
            var questionView = createTestQuestion(i, answersCount);
            tasksVBox.getChildren().add(questionView);
            questionViews.add(questionView);
        }
    }

    private OneOfFourQuestionView createTestQuestion(int correctIndex, int answersCount) {
        List<Integer> otherIndexes = new ArrayList<>();
        for (int i = 0; i < translations.size(); ++i) {
            otherIndexes.add(i);
        }

        otherIndexes.remove((Integer)correctIndex);
        Collections.shuffle(otherIndexes);

        List<String> incorrectAnswers = otherIndexes.subList(0, answersCount - 1).stream()
                .map(translations::get)
                .map(Translation::getTarget)
                .map(Word::getText)
                .collect(Collectors.toList());

        return new OneOfFourQuestionView(translations.get(correctIndex), incorrectAnswers);
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
        double questionHeight = parentHeight / Math.max(1, questionViews.size());
        questionViews.forEach(questionView -> questionView.setPrefSize(parentWidth, questionHeight));
    }
}