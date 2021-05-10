package egl.client.view.info;

import egl.client.service.FxmlService;
import egl.client.view.pane.CustomBorderPane;
import egl.core.model.DescribedEntity;
import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NameDescriptionInfoView<T extends DescribedEntity> extends CustomBorderPane implements EntityInfoView<T> {

    @FXML private Label nameLabel;
    @FXML private TextField nameTextField;
    @FXML private TextArea descriptionTextArea;

    public NameDescriptionInfoView() {
        FxmlService.loadView(this, NameDescriptionInfoView.class, true);
    }

    public void setNameTitle(String nameTitle) {
        nameLabel.setText(nameTitle);
    }

    public String getNameTitle() {
        return nameLabel.getText();
    }

    @Override
    public void initData(T entity, boolean isCreated) {
        nameTextField.setText(entity.getName());
        descriptionTextArea.setText(entity.getDescription());
    }

    @Override
    public void validateData() {
        if (nameTextField.getText().isBlank()) {
            throw new IllegalArgumentException(String.format("%s не может быть пустым", nameLabel.getText()));
        }
    }

    @Override
    public void fillData(T entity) {
        entity.setName(nameTextField.getText());
        entity.setDescription(descriptionTextArea.getText());
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        nameTextField.setPrefWidth(parentWidth * 0.9);
        descriptionTextArea.setPrefWidth(parentWidth);
    }
}
