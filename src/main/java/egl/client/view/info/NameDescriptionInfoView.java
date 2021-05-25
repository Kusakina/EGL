package egl.client.view.info;

import egl.client.service.FxmlService;
import egl.client.view.pane.CustomBorderPane;
import egl.client.view.text.LabeledTextField;
import egl.core.model.DescribedEntity;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class NameDescriptionInfoView<T extends DescribedEntity> extends CustomBorderPane implements EntityInfoView<T> {

    @FXML private LabeledTextField nameTextField;
    @FXML private TextArea descriptionTextArea;

    public NameDescriptionInfoView() {
        FxmlService.loadView(this, NameDescriptionInfoView.class);
    }

    public void setNameTitle(String nameTitle) {
        nameTextField.setLabel(nameTitle);
    }

    public String getNameTitle() {
        return nameTextField.getLabel();
    }

    @Override
    public void initData(T entity, boolean isCreated) {
        var name = (null != entity.getName() ? entity.getName() : "");
        nameTextField.setText(name);

        var description = (null != entity.getDescription() ? entity.getDescription() : "");
        descriptionTextArea.setText(description);
    }

    @Override
    public void validateData() {
        if (nameTextField.getText().isBlank()) {
            throw new IllegalArgumentException(String.format("%s не может быть пустым", getNameTitle()));
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
