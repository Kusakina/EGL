package egl.client.controller.task.category;

import egl.client.model.topic.category.Translation;
import egl.client.service.FxmlService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

public class FixedIndexView extends GridPane implements Initializable {
    @FXML
    private Text indexAnswer;
    @FXML
    private Text targetText;

    @Getter
    private final Translation translation;

    private int Index;

    FixedIndexView(Translation translation, int Index) {
        this.translation = translation;
        this.Index = Index;
        FxmlService.loadView(this, FixedIndexView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        targetText.setText(translation.getTarget().getText());
        indexAnswer.setText(String.valueOf(Index+1));
        setMargin(targetText, new Insets(20));
        setColumnIndex(indexAnswer, 0);
        setColumnIndex(targetText, 1);
        setRowIndex(indexAnswer, 0);
        setRowIndex(targetText, 0);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
    }


}
