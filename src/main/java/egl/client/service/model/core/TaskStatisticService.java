package egl.client.service.model.core;

import java.util.HashMap;
import java.util.Map;

import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.repository.core.statistic.TaskStatisticRepository;
import egl.client.service.model.AbstractEntityService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public abstract class TaskStatisticService
        extends AbstractEntityService<TaskStatistic, TaskStatisticRepository> {

    @Data
    @RequiredArgsConstructor
    private static class TaskStatisticId {

        private final TopicStatistic topicStatistic;
        private final String taskName;
    }

    protected final Map<TaskStatisticId, TaskStatistic> taskStatisticCache;

    public TaskStatisticService(TaskStatisticRepository taskStatisticRepository) {
        super(taskStatisticRepository);
        this.taskStatisticCache = new HashMap<>();
    }

    private TaskStatistic findByInPersistence(TaskStatisticId taskStatisticId) {
        var taskStatistic = repository
                .findByTopicStatisticAndTaskName(
                        taskStatisticId.getTopicStatistic(),
                        taskStatisticId.getTaskName()
                );

        return taskStatistic.orElseGet(
                () -> save(
                        new TaskStatistic(
                                taskStatisticId.getTopicStatistic(),
                                taskStatisticId.getTaskName(),
                                Result.NONE
                        )
                )
        );
    }

    public TaskStatistic findBy(TopicStatistic topicStatistic, Task task) {
        var taskName = task.getName();
        var taskStatisticId = new TaskStatisticId(topicStatistic, taskName);

        return taskStatisticCache.computeIfAbsent(
                taskStatisticId, this::findByInPersistence
        );
    }

    public void update(TopicStatistic topicStatistic, Task task, Result result) {
        var taskStatistic = findBy(topicStatistic, task);
        if (taskStatistic.updateBy(result)) {
            save(taskStatistic);
        }
    }

    @Override
    public void remove(TaskStatistic entity) {
        super.remove(entity);
        taskStatisticCache.remove(
                new TaskStatisticId(
                    entity.getTopicStatistic(), entity.getTaskName()
                )
        );
    }
}
