package egl.client.controller.task.category;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

@Component
@FxmlView
public class MatchIndexTaskController extends AbstractTaskController {

    private static final int MAX_TASKS_COUNT = 8;
    private List<Translation> translations;
    List<InputIndexView> inputIndexViews;
    List<FixedIndexView> fixedIndexViews;

    @FXML
    private GridPane tasksGridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void prepareTasks(){
        Category category = (Category) controllerTopic;

        this.translations = category.getTranslations();
        Collections.shuffle(translations);

        final int tasksCount = Math.min(MAX_TASKS_COUNT, translations.size());

        this.inputIndexViews = new ArrayList<>();
        this.fixedIndexViews = new ArrayList<>();
        List<Integer> position = new ArrayList<>();
        for (int i = 0; i<tasksCount; ++i)
            position.add(i);
        Collections.shuffle(position);

        for (int i = 0; i < tasksCount; ++i) {
            var questionView = createTestQuestion(i, tasksCount);
            var answerView = createTestAnswer(position.get(i), i);
            tasksGridPane.getColumnConstraints().add(new ColumnConstraints(550));
            tasksGridPane.add(questionView, 0, i);
            tasksGridPane.add(answerView,  1, i);
            inputIndexViews.add(questionView);
            fixedIndexViews.add(answerView);
        }

    }
    private InputIndexView createTestQuestion(int correctIndex, int taskCount)
    {
        return new InputIndexView(translations.get(correctIndex), taskCount);
    }

    private FixedIndexView createTestAnswer(int Index, int IncorrectIndex)
    {
        return new FixedIndexView(translations.get(Index), IncorrectIndex);
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
