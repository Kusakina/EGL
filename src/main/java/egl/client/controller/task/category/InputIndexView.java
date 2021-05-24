package egl.client.controller.task.category;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import egl.client.model.topic.category.Translation;
import egl.client.service.FxmlService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Getter;

public class InputIndexView extends GridPane implements Initializable {

    //private static final int TASKS_COUNT = 7;
    @FXML private TextField inputIndexTextField;
    @FXML private Text sourceText;

    @Getter
    private final Translation translation;

    InputIndexView(Translation translation) {
        this.translation = translation;
        FxmlService.loadView(this, InputIndexView.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getText();
            //if (input.matches("[0-9]*") && ((taskCount < 10 && change.getControlNewText().length()<2) ||
                    //(taskCount >= 10 && change.getControlNewText().length()<=2))) {
            if (input.matches("[1-8]*") && change.getControlNewText().length()<2) {
                return change;
            }
            return null;
        };
        inputIndexTextField.setTextFormatter(new TextFormatter<String>(integerFilter));
        sourceText.setText(translation.getSource().getText());
        setMargin(sourceText, new Insets(20));
        inputIndexTextField.setPrefSize(45,45);
        setMargin(inputIndexTextField, new Insets(15));
        setColumnIndex(inputIndexTextField, 0);
        setColumnIndex(sourceText, 1);
        setRowIndex(inputIndexTextField, 0);
        setRowIndex(sourceText, 0);
        setHalignment(sourceText, HPos.LEFT);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        super.setPrefSize(parentWidth, parentHeight);

        for (ColumnConstraints columnConstraints : getColumnConstraints()) {
            columnConstraints.setPercentWidth(50);
        }
    }


    public boolean indexNotPresent() {
        return inputIndexTextField.getText().isBlank();
    }

    public int getIndex() {
        return Integer.parseInt(inputIndexTextField.getText()) - 1;
    }
}
