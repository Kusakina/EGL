package egl.client.controller.topic.info;

import egl.client.model.local.topic.Theory;
import egl.client.view.info.EntityInfoView;
import javafx.scene.control.TextArea;

public class TheoryTextArea extends TextArea implements EntityInfoView<Theory>  {

    @Override
    public void initData(Theory theory, boolean isCreated) {
        setText(theory.getText());
    }

    @Override
    public void validateData() {

    }

    @Override
    public void fillData(Theory theory) {
        theory.setText(getText());
    }
}
