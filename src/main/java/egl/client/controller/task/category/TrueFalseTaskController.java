package egl.client.controller.task.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
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

@Component
@FxmlView
public class TrueFalseTaskController extends AbstractTaskController {

    private static final int MAX_QUESTIONS_COUNT = 8;

    @FXML
    private VBox tasksVBox;

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {

    }

    @Override
    protected void prepareToStart() {
        Category category = (Category) controllerTopic;
        List<Translation> tasks = category.getTranslations();

        int numTasks = Math.min(MAX_QUESTIONS_COUNT, tasks.size());

        String[] ansTrue = new String[numTasks];

        Collections.shuffle(tasks);
        tasks.subList(0, numTasks - 1);
        List<Translation> translations = new ArrayList<>(tasks);
        Collections.shuffle(translations);
        for (int i = 0; i < numTasks; i++) {
            addTestQuestion(i, tasks, translations, ansTrue);
        }
    }
    private void addTestQuestion(int i, List <Translation> tasks, List <Translation> translations, String[] ansTrue) {
        var vBoxChildren = tasksVBox.getChildren();
        GridPane questionPane = new GridPane();
        questionPane.setStyle("-fx-font: 18 arial;");
        String source = tasks.get(i).getSource().getText();
        String target = translations.get(i).getTarget().getText();
        if (source.equals(target)) ansTrue[i] = "True";
        else ansTrue[i] = "False";
        Text number = new Text(Integer.toString(i+1) + ". ");
        questionPane.add(number, 0, 0);
        questionPane.getColumnConstraints().add(new ColumnConstraints(40));
        Text sourceText = new Text(source);
        questionPane.add(sourceText, 1, 0);
        questionPane.getColumnConstraints().add(new ColumnConstraints(150));
        Text dash = new Text("-");
        questionPane.add(dash, 2, 0);
        questionPane.getColumnConstraints().add(new ColumnConstraints(100));
        Text targetText = new Text(target);
        questionPane.add(targetText, 3, 0);
        ObservableList<String> change = FXCollections.observableArrayList("True", "False");
        ComboBox<String> changeComboBox = new ComboBox<String>(change);
        changeComboBox.setStyle("-fx-font-weight:bold;" +
                " -fx-text-background-color: blue;" +
                "-fx-font-size:14px;" +
                "-fx-pref-width: 130;");
        changeComboBox.setPromptText("True/False");
        questionPane.add(changeComboBox, 4, 0);
        questionPane.getColumnConstraints().add(new ColumnConstraints(100));
        questionPane.getRowConstraints().add(new RowConstraints(40));
        vBoxChildren.add(questionPane);
    }

    @Override
    protected void prepareToFinish() {

    }
}
