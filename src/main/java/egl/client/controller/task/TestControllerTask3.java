package egl.client.controller.task;

import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.core.model.task.Result;
import egl.core.model.task.Task;
import egl.core.model.topic.Topic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.util.function.Consumer;
@Component
@FxmlView
public class TestControllerTask3 extends AbstractTaskController {
    @FXML
    private VBox tasksVBox;

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {

    }

    @Override
    protected void prepareToStart() {
        int numTasks = 7;
        String[] ansTrue = new String[numTasks];
        Category category = (Category) controllerTopic;
        List<Translation> tasks = category.getTranslations();
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
