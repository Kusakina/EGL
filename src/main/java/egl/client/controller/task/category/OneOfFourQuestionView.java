package egl.client.controller.task.category;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import egl.client.model.topic.category.Translation;
import egl.client.service.FxmlService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class OneOfFourQuestionView extends GridPane implements Initializable {

    @FXML private Text questionText;

    private RadioButton[] answerRadioButtons;

    private final Translation correctTranslation;
    private final List<String> incorrectAnswers;

    OneOfFourQuestionView(Translation correctTranslation, List<String> incorrectAnswers) {
        this.correctTranslation = correctTranslation;
        this.incorrectAnswers = incorrectAnswers;

        FxmlService.loadView(this, OneOfFourQuestionView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int answersCount = incorrectAnswers.size() + 1;

        questionText.setText(correctTranslation.getSource().getText());
        setMargin(questionText, new Insets(20));
        setColumnSpan(questionText, answersCount);
        setColumnIndex(questionText, 0);
        setRowIndex(questionText, 0);
        setHalignment(questionText, HPos.LEFT);

        this.answerRadioButtons = new RadioButton[answersCount];

        List<String> answers = new ArrayList<>(incorrectAnswers);
        answers.add(correctTranslation.getTarget().getText());

        Collections.shuffle(answers);

        for (int i = 0; i < answersCount; ++i) {
            String answer = answers.get(i);
            RadioButton ansRadioButton = answerRadioButtons[i] = new RadioButton(answer);
            this.add(ansRadioButton, i, 1, 1, 1);

            getColumnConstraints().add(new ColumnConstraints());
        }

        ToggleGroup answerToggleGroup = new ToggleGroup();
        for (RadioButton answerRadioButton : answerRadioButtons) {
            answerRadioButton.setToggleGroup(answerToggleGroup);
        }
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        super.setPrefSize(parentWidth, parentHeight);

        getRowConstraints().get(1).setPercentHeight(40);
        for (ColumnConstraints columnConstraints : getColumnConstraints()) {
            columnConstraints.setPercentWidth(100.0 / Math.max(1, incorrectAnswers.size()));
        }
    }
}
