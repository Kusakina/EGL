package egl.client.controller.info;

import egl.core.model.DescribedEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DescribedEntityInfoController<T extends DescribedEntity> extends AbstractDescribedEntityInfoController<T> {

    @FXML private Label nameLabel;
    @FXML private TextField nameTextField;
    @FXML private TextArea descriptionTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void validateData() {
        if (nameTextField.getText().isBlank()) {
            throw new IllegalArgumentException(String.format("%s не может быть пустым", nameLabelText));
        }
    }

    @Override
    public void fillData() {
        entity.setName(nameTextField.getText());
        entity.setDescription(descriptionTextArea.getText());
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        nameTextField.setPrefWidth(parentWidth * 0.9);
        descriptionTextArea.setPrefWidth(parentWidth);
    }

    @Override
    public void prepareToShow() {
        nameLabel.setText(nameLabelText);
    }

    @Override
    public void prepareToClose() {

    }
}
