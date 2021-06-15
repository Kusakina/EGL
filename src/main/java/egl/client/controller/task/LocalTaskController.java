package egl.client.controller.task;

import java.util.function.Consumer;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.service.model.local.LocalTopicInfoService;
import egl.client.service.model.local.SpecificLocalTopicInfoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class LocalTaskController<T extends DatabaseEntity> extends AbstractTaskController {

    private final LocalTopicInfoService localTopicInfoService;
    private final SpecificLocalTopicInfoService<T> specificLocalTopicInfoService;

    protected LocalTopicInfo localTopicInfo;
    protected T specificLocalTopic;

    @Override
    public void setContext(Task controllerTask, Topic controllerTopic, Consumer<Result> resultConsumer) {
        super.setContext(controllerTask, controllerTopic, resultConsumer);
        this.localTopicInfo = localTopicInfoService.findBy(controllerTopic);
        this.specificLocalTopic = specificLocalTopicInfoService.findBy(localTopicInfo);
    }
}
