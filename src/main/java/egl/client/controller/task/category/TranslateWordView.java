package egl.client.controller.task.category;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.model.local.topic.category.Translation;
import egl.client.service.FxmlService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TranslateWordView extends GridPane implements Initializable {

    @FXML
    private Text translationText;
    @FXML
    private Text numberText;

    private TextField answerTextField;

    private final Translation translation;
    private final int sourceOrTarget;
    private final int index;
    private String expectedText;

    TranslateWordView(Translation translation, int SourceOrTarget, int index) {
        this.translation = translation;
        this.sourceOrTarget = SourceOrTarget;
        this.index = index;

        FxmlService.loadView(this, TranslateWordView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGridLinesVisible(true);
        numberText.setText(" " + (index + 1) + ".");
        this.answerTextField = new TextField();
        if (sourceOrTarget == 0) {
            translationText.setText(translation.getSource().getText());
            this.expectedText = translation.getTarget().getText();
        }
        else {
            translationText.setText(translation.getTarget().getText());
            this.expectedText = translation.getSource().getText();
        }
        setColumnIndex(translationText, sourceOrTarget + 1);
        this.add(answerTextField, 2 - sourceOrTarget, 0, 1, 1);
        setMargin(translationText, new Insets(9));
    }

    public boolean isCorrect() {
        return answerTextField.getText().equals(expectedText);
    }
}
