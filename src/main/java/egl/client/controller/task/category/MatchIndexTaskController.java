package egl.client.controller.task.category;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
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

    @FXML
    private VBox tasksVBoxes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void prepareTasks(){
        int taskCount = 7;
        Category category = (Category) controllerTopic;
        List<Translation> task = category.getTranslations();
        List<Integer> position = new ArrayList<>();
        int[] trueAnswersSecondTask = new int[taskCount];//запоминаем правильные ответы
        for (int i = 0; i<taskCount; ++i)
            position.add(i);
        Collections.shuffle(task);
        Collections.shuffle(position);
        for (int i = 0; i<taskCount; ++i) {
            trueAnswersSecondTask[i] = position.get(i) + 1;
            //trueAnswersFirstTask[i] = position.get(0);
            //addTestQuestion(task, translations, position, i);
            var vBoxChildren = tasksVBoxes.getChildren();
            GridPane questionPane = new GridPane();
            questionPane.setStyle("-fx-font: 22 arial;");
            TextField questionField = new TextField();
            questionField.setStyle("-fx-font: 22 arial;");
            questionPane.getColumnConstraints().add(new ColumnConstraints(40));

            UnaryOperator<TextFormatter.Change> integerFilter = change -> {
                String input = change.getText();
                if (input.matches("[0-9]*") && ((taskCount < 10 && change.getControlNewText().length()<2) ||
                        (taskCount >= 10 && change.getControlNewText().length()<=2))) {
                    return change;
                }
                return null;
            };
            questionField.setTextFormatter(new TextFormatter<String>(integerFilter));

            questionPane.add(questionField, 0, 0, 1, 1);
            Text questionText = new Text(" " + task.get(position.get(i)).getSource().getText());
            questionPane.getColumnConstraints().add(new ColumnConstraints(500));
            questionPane.getRowConstraints().add(new RowConstraints(70));
            questionPane.add(questionText, 1, 0, 3, 1);
            Text questionAnswer = new Text(i+1 + ". " + task.get(i).getTarget().getText());
            questionPane.add(questionAnswer, 5, 0, 3, 1);

            vBoxChildren.add(questionPane);
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
