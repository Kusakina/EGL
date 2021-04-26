package egl.client.controller;

import java.util.ArrayList;
import java.util.List;

import egl.model.Result;
import egl.model.Task;
import egl.model.Test;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class TestController extends TaskController {

    private List<TaskController> taskControllers;
    private List<Result> taskResults;

    public TestController() {
        super();

        this.taskControllers = new ArrayList<>();
        this.taskResults = new ArrayList<>();
    }

    public void initialize(Test test) {
        for (Task task : test.getTasks()) {

        }
    }

    @Override
    protected void startTask() {

    }

    @Override
    protected void finishTask() {

    }
}
