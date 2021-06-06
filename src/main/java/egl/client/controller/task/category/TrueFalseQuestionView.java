package egl.client.controller.task.category;

import egl.client.model.topic.category.Translation;
import egl.client.service.FxmlService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TrueFalseQuestionView extends GridPane implements Initializable {
    @FXML
    private Text questionText;
    @FXML
    private Text answerText;
    @FXML
    private Text dash;
    @FXML
    private Text number;
    private final Translation translation;
    private final Translation answer;
    private final int index;
    TrueFalseQuestionView(Translation translation, Translation answer, int index) {
        this.translation = translation;
        this.answer = answer;
        this.index = index;

        FxmlService.loadView(this, TrueFalseQuestionView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        number.setText(Integer.toString(index + 1) + ".");
        questionText.setText(translation.getSource().getText());
        answerText.setText(answer.getTarget().getText());
        setColumnIndex(questionText, 1);
        setColumnIndex(dash, 2);
        setColumnIndex(answerText, 3);
        ObservableList<String> change = FXCollections.observableArrayList("True", "False");
        ComboBox<String> changeComboBox = new ComboBox<String>(change);
        changeComboBox.setStyle("-fx-font-weight:bold;" +
                " -fx-text-background-color: blue;" +
                "-fx-font-size:25px;" +
                "-fx-pref-width: 230;");
        changeComboBox.setPromptText("True/False");
        this.add(changeComboBox, 4, 0, 1, 1);
        setMargin(questionText, new Insets(15));
    }
}
