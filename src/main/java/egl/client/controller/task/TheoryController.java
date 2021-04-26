package egl.client.controller.task;

import egl.client.model.topic.LocalTopic;
import egl.core.model.task.Task;
import egl.core.model.topic.Topic;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class TheoryController extends TaskController {

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
