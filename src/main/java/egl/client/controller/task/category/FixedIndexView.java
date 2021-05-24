package egl.client.controller.task.category;

import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.client.service.FxmlService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Getter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class FixedIndexView extends GridPane implements Initializable {
    @FXML
    private Text IndexAnswer;
    @FXML
    private Text targetText;

    @Getter
    private final Translation translation;

    private static int TASKS_COUNT = 1;

    FixedIndexView(Translation translation) {
        this.translation = translation;
        FxmlService.loadView(this, FixedIndexView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //List<String> answers = new ArrayList<>(translation.getTarget().getText());
        //Collections.shuffle(answers);
        //targetText.setText(answers);
        targetText.setText(translation.getTarget().getText());
        IndexAnswer.setText(String.valueOf(TASKS_COUNT));
        ++TASKS_COUNT;
        setMargin(targetText, new Insets(20));
        setColumnIndex(IndexAnswer, 0);
        setColumnIndex(targetText, 1);
        setRowIndex(IndexAnswer, 0);
        setRowIndex(targetText, 0);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        super.setPrefSize(parentWidth, parentHeight);

        for (ColumnConstraints columnConstraints : getColumnConstraints()) {
            columnConstraints.setPercentWidth(50);
        }
    }


}
