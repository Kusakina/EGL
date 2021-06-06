package egl.client.controller.task.category;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.model.local.topic.category.Translation;
import egl.client.service.FxmlService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Getter;

public class FixedIndexView extends GridPane implements Initializable {

    @FXML
    private Text indexAnswer;
    @FXML
    private Text targetText;

    @Getter
    private final Translation translation;

    private final int index;

    FixedIndexView(Translation translation, int index) {
        this.translation = translation;
        this.index = index;
        FxmlService.loadView(this, FixedIndexView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        targetText.setText(translation.getTarget().getText());
        indexAnswer.setText(String.valueOf(index + 1));
        setMargin(targetText, new Insets(20));
        setColumnIndex(indexAnswer, 0);
        setColumnIndex(targetText, 1);
        setRowIndex(indexAnswer, 0);
        setRowIndex(targetText, 0);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        super.setPrefSize(parentWidth, parentHeight);
    }
}
