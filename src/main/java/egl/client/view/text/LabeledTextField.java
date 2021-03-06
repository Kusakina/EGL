package egl.client.view.text;

import egl.client.service.FxmlService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LabeledTextField extends HBox {

    @FXML private Label label;
    @FXML private TextField textField;

    public LabeledTextField() {
        FxmlService.loadView(this, LabeledTextField.class, true);
    }

    public void setLabel(String labelText) {
        label.setText(labelText);
    }

    public String getLabel() {
        return label.getText();
    }

    public void setText(String text) { textField.setText(text);}

    public String getText() {
        return textField.getText();
    }

    @Override
    protected void setWidth(double width) {
        super.setWidth(width);
        textField.setPrefWidth(width * 0.5);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        super.setPrefSize(parentWidth, parentHeight);
        setWidth(parentWidth);
    }
}
