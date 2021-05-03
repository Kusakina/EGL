package egl.client.controller.task;

import egl.client.model.topic.LocalTopic;

public abstract class TheoryTaskController extends AbstractTaskController {

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
