package egl.client.controller.task;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.model.topic.LocalTopic;

public abstract class TheoryTaskController extends AbstractTaskController {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        descriptionTextArea.setEditable(false);
    }

    @Override
    protected void prepareToStart() {
        prepareTheory();
        prepareSpecificTheory();
    }

    private void prepareTheory() {
        LocalTopic localTopic = (LocalTopic) controllerTopic;
        descriptionTextArea.setText(localTopic.getTheory().getText());
    }

    protected abstract void prepareSpecificTheory();

    @Override
    protected void prepareToFinish() {
        result.registerAnswer(true);
    }
}
