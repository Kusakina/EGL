package egl.client.controller.task;

import java.util.function.Consumer;

import egl.client.controller.Controller;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;

public interface TaskController extends Controller {

    String FINISH_BUTTON_TEXT = "Завершить";

    void setContext(Task controllerTask, Topic controllerTopic, Consumer<Result> resultConsumer);
}
