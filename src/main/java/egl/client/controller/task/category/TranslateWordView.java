package egl.client.controller.task.category;

import egl.client.model.topic.category.Translation;
import egl.client.service.FxmlService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TranslateWordView extends GridPane implements Initializable {
    @FXML
    private Text translationText;
    @FXML
    private Text number;
    private final Translation translation;
    private int SourceOrTarget;
    private final int index;
    TranslateWordView(Translation translation, int SourceOrTarget, int index) {
        this.translation = translation;
        this.SourceOrTarget = SourceOrTarget;
        this.index = index;

        FxmlService.loadView(this, TranslateWordView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGridLinesVisible(true);
        number.setText(" " + Integer.toString(index + 1) + ".");
        TextField answer = new TextField();
        if (SourceOrTarget == 0) {
            translationText.setText(translation.getSource().getText());
        }
        else {
            translationText.setText(translation.getTarget().getText());
        }
        setColumnIndex(translationText, SourceOrTarget + 1);
        this.add(answer, 2 - SourceOrTarget, 0, 1, 1);
        setMargin(translationText, new Insets(9));
    }
}
