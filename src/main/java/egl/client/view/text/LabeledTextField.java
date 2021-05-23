package egl.client.view.text;

import egl.client.service.FxmlService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LabeledTextField extends VBox {

    @FXML private Label label;
    @FXML private TextField textField;

    public LabeledTextField() {
        FxmlService.loadView(this, LabeledTextField.class, true);
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    public String getLabel() {
        return label.getText();
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        textField.setPrefWidth(parentWidth * 0.9);
    }
}
