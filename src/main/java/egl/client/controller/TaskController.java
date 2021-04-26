package egl.client.controller;

import java.util.function.Consumer;

import egl.model.Result;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

public abstract class TaskController implements Controller {

    protected Result result;
    private Consumer<Result> resultConsumer;

    protected TaskController() {
        this.result = new Result();
    }

    protected abstract void startTask();

    public void start(Consumer<Result> resultConsumer) {
        this.resultConsumer = resultConsumer;
        startTask();
    }

    protected abstract void finishTask();

    protected void finish() {
        finishTask();

        resultConsumer.accept(result);
    }
}
