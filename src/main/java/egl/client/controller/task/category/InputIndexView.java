package egl.client.controller.task.category;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import egl.client.model.local.topic.category.Translation;
import egl.client.service.FxmlService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Getter;

public class InputIndexView extends GridPane implements Initializable {

    public static final int INCORRECT_INDEX = -1;
    private static final int DIGIT_WIDTH = 50;

    @FXML private TextField inputIndexTextField;
    @FXML private Text sourceText;

    @Getter
    private final Translation translation;

    private final int tasksCount;

    InputIndexView(Translation translation, int tasksCount) {
        this.translation = translation;
        this.tasksCount = tasksCount;
        FxmlService.loadView(this, InputIndexView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final int maxIndexLength = String.valueOf(tasksCount).length();

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            var input = change.getControlNewText();

            boolean isNumber = input.matches("[0-9]*");
            boolean hasCorrectLength = input.length() <= maxIndexLength;

            return (isNumber && hasCorrectLength) ? change : null;
        };

        inputIndexTextField.setTextFormatter(new TextFormatter<String>(integerFilter));
        sourceText.setText(translation.getSource().getText());
        setMargin(sourceText, new Insets(20));
        setMargin(inputIndexTextField, new Insets(15));
        inputIndexTextField.setPrefWidth(maxIndexLength * DIGIT_WIDTH);
        setColumnIndex(inputIndexTextField, 0);
        setColumnIndex(sourceText, 1);
        setRowIndex(inputIndexTextField, 0);
        setRowIndex(sourceText, 0);
        setHalignment(sourceText, HPos.LEFT);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        super.setPrefSize(parentWidth, parentHeight);
        inputIndexTextField.setPrefHeight(parentHeight);
    }

    public int getIndex() {
        var indexText = inputIndexTextField.getText();
        if (indexText.isBlank()) return INCORRECT_INDEX;

        int index = Integer.parseInt(indexText) - 1;
        if (index < 0 || tasksCount <= index) return INCORRECT_INDEX;

        return index;
    }
}
