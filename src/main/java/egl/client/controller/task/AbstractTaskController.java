package egl.client.controller.task;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import egl.client.controller.Controller;
import egl.core.model.task.Result;
import egl.core.model.task.Task;
import egl.core.model.topic.Topic;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public abstract class AbstractTaskController implements Controller {

    @FXML protected TextArea descriptionTextArea;

    private Task controllerTask;
    private Topic controllerTopic;
    private Consumer<Result> resultConsumer;

    protected Result result;

    protected AbstractTaskController() {
        this.result = new Result();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setContext(Task controllerTask, Topic controllerTopic, Consumer<Result> resultConsumer) {
        this.controllerTask = controllerTask;
        this.controllerTopic = controllerTopic;
        this.resultConsumer = resultConsumer;
    }

    protected abstract void prepareToStart(Task controllerTask, Topic controllerTopic);

    @Override
    public void prepareToShow() {
        prepareToStart(controllerTask, controllerTopic);
    }

    protected abstract void prepareToFinish();

    @Override
    public void prepareToClose() {
        prepareToFinish();
        resultConsumer.accept(result);
    }
}
