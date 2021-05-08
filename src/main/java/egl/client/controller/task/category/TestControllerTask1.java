package egl.client.controller.task.category;

import java.net.URL;
import java.util.*;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class TestControllerTask1 extends AbstractTaskController {

    @FXML
    private VBox tasksVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void prepareTasks() {
        int taskCount = 7;
        Category category = (Category) controllerTopic;
        List<Translation> task = category.getTranslations();
        List<Translation> translations = category.getTranslations();
        List<Integer> position = new ArrayList<>();
        int[] trueAnswersFirstTask = new int[taskCount];//запоминаем правильные ответы
        for (int i = 0; i<4; ++i)
            position.add(i);
        Collections.shuffle(task);
        for (int i = 0; i<taskCount; ++i) {
            Collections.shuffle(translations);
            Collections.shuffle(position);
            trueAnswersFirstTask[i] = position.get(0);
            addTestQuestion(task, translations, position, i);
        }
//
    }

    private void addTestQuestion(List<Translation> task, List<Translation> translations, List<Integer> position, int j) {
//        tasksVBox.setPrefSize(500, 500);
        var vBoxChildren = tasksVBox.getChildren();

        GridPane questionPane = new GridPane();
//        questionPane.setPrefSize(200, 200);

        Text questionText = new Text(task.get(j).getSource().getText());
        questionText.setStyle("-fx-font: 26 arial;");
        //questionText.setStyle("-fx-font: 26 ;");
        questionPane.add(questionText, 0, 0, 4, 1);
        questionPane.setStyle("-fx-font: 18 arial;");

        int answersCount = 4;
        RadioButton[] ansRadioButtons = new RadioButton[answersCount];

        for (int i = 0, q = 0; i < answersCount; ++i, ++q) {
            questionPane.getColumnConstraints().add(new ColumnConstraints(250));
            questionPane.getRowConstraints().add(new RowConstraints(40));
            RadioButton ansRadioButton;
            if (i == position.get(0)) {
                ansRadioButton = ansRadioButtons[i] = new RadioButton(task.get(j).getTarget().getText());
            }
            else {
                if (translations.get(i).getTarget().getText() == task.get(j).getTarget().getText()) {
                    ++q;
                }
                ansRadioButton = ansRadioButtons[i] = new RadioButton(translations.get(q).getTarget().getText());
            }
            questionPane.add(ansRadioButton, i, 1, 1, 1);
        }

        ToggleGroup ansToogleGroup = new ToggleGroup();
        for (RadioButton ansRadioButton : ansRadioButtons) {
            ansRadioButton.setToggleGroup(ansToogleGroup);
        }

        vBoxChildren.add(questionPane);
    }


    @Override
    protected void prepareToStart() {
        prepareTasks();
    }

    @Override
    protected void prepareToFinish() {

    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {

    }
}
