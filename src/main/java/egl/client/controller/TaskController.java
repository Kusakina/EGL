package egl.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import egl.model.Result;
import egl.model.Task;
import egl.model.Topic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class TaskController implements Controller {

    @FXML public Button finishButton;

    protected Result result;

    protected Topic topic;
    private Consumer<Result> resultConsumer;

    protected TaskController() {
        this.result = new Result();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.finishButton.setOnAction(actionEvent -> this.finish());
    }

    protected abstract void startTask(Task task);

    public void start(
            Task task,
            Topic topic,
            Consumer<Result> resultConsumer
    ) {
        this.resultConsumer = resultConsumer;
        this.topic = topic;
        startTask(task);
    }

    protected abstract void finishTask();

    protected void finish() {
        finishTask();

        resultConsumer.accept(result);
    }
}
