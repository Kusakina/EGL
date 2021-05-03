package egl.client.controller.task;

import java.util.function.Consumer;

import egl.client.controller.Controller;
import egl.core.model.task.Result;
import egl.core.model.task.Task;
import egl.core.model.topic.Topic;

public interface TaskController extends Controller {

    String FINISH_BUTTON_TEXT = "Завершить";

    void setContext(Task controllerTask, Topic controllerTopic, Consumer<Result> resultConsumer);
}
