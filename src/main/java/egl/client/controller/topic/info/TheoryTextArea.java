package egl.client.controller.topic.info;

import egl.client.model.local.topic.Theory;
import egl.client.model.local.topic.category.Category;
import egl.client.view.info.EntityInfoView;
import javafx.scene.control.TextArea;

public class TheoryTextArea extends TextArea implements EntityInfoView<Category>  {

    @Override
    public void initData(Category category, boolean isCreated) {
        var theory = category.getTheory();
        var text= (null != theory && null != theory.getText() ? theory.getText() : "");
        setText(text);
    }

    @Override
    public void validateData() {

    }

    @Override
    public void fillData(Category category) {
        var theory = category.getTheory();
        if (null == theory) {
            category.setTheory(theory = new Theory());
        }

        theory.setText(getText());
    }
}
