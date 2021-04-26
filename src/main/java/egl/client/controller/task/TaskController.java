package egl.client.controller.task;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import egl.client.controller.Controller;
import egl.core.model.task.Result;
import egl.core.model.task.Task;
import egl.core.model.topic.Topic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public abstract class TaskController extends Controller {

    @FXML protected TextArea descriptionTextArea;
    @FXML protected Button finishButton;

    private Consumer<Result> resultConsumer;

    protected Result result;

    protected TaskController() {
        this.result = new Result();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.finishButton.setOnAction(this::processFinish);
    }

    protected abstract void prepareToStart(Task controllerTask, Topic controllerTopic);

    public void start(
            Task task,
            Topic topic,
            Consumer<Result> resultConsumer
    ) {
        this.resultConsumer = resultConsumer;
        prepareToStart(task, topic);
    }

    protected abstract void prepareToFinish();

    protected void finish() {
        prepareToFinish();
        resultConsumer.accept(result);
    }

    private void processFinish(ActionEvent event) {
        finish();
        closeWindow();
    }
}
