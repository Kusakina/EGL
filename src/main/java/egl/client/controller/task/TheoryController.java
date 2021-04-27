package egl.client.controller.task;

import egl.client.model.topic.LocalTopic;
import egl.core.model.task.Task;
import egl.core.model.topic.Topic;

public abstract class TheoryController extends TaskController {

    @Override
    protected void prepareToStart(Task controllerTask, Topic controllerTopic) {
        LocalTopic localTopic = (LocalTopic) controllerTopic;
        descriptionTextArea.setText(localTopic.getTheory().getText());
    }

    @Override
    protected void prepareToFinish() {
        result.registerAnswer(true);
    }
}
