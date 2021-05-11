package egl.client.controller.task;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import egl.core.model.task.Result;
import egl.core.model.task.Task;
import egl.core.model.topic.Topic;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public abstract class AbstractTaskController implements TaskController {

    @FXML protected TextArea descriptionTextArea;

    protected Task controllerTask;
    protected Topic controllerTopic;
    private Consumer<Result> resultConsumer;

    protected Result result;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void setContext(Task controllerTask, Topic controllerTopic, Consumer<Result> resultConsumer) {
        this.controllerTask = controllerTask;
        this.controllerTopic = controllerTopic;
        this.resultConsumer = resultConsumer;
    }

    protected abstract void prepareToStart();

    @Override
    public void prepareToShow() {
        this.result = new Result();
        prepareToStart();
    }

    protected abstract void prepareToFinish();

    @Override
    public void prepareToClose() {
        prepareToFinish();
        resultConsumer.accept(result);
    }
}
