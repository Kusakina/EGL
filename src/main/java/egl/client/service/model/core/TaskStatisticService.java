package egl.client.service.model.core;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.repository.core.statistic.TaskStatisticRepository;
import egl.client.service.model.EntityServiceException;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public abstract class TaskStatisticService
        extends CachedEntityService<
            TaskStatistic,
            TaskStatisticRepository,
            TaskStatisticService.TaskStatisticId
        > {

    @Data
    @RequiredArgsConstructor
    static class TaskStatisticId implements EntityId {

        private final TopicStatistic topicStatistic;
        private final String taskName;
    }

    protected TaskStatisticService(TaskStatisticRepository repository) {
        super(repository);
    }

    @Override
    protected Optional<TaskStatistic> findById(TaskStatisticId taskStatisticId) {
        try {
            return repository.findByTopicStatisticAndTaskName(
                    taskStatisticId.getTopicStatistic(),
                    taskStatisticId.getTaskName()
            );
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }

    @Override
    protected TaskStatistic createWith(TaskStatisticId taskStatisticId) {
        return new TaskStatistic(
                taskStatisticId.getTopicStatistic(),
                taskStatisticId.getTaskName(),
                Result.NONE
        );
    }

    @Override
    protected TaskStatisticId getIdOf(TaskStatistic entity) {
        return new TaskStatisticId(
                entity.getTopicStatistic(), entity.getTaskName()
        );
    }

    public static String getTaskName(Task task) {
        return task.getSceneName();
    }

    private TaskStatisticId toId(TopicStatistic topicStatistic, Task task) {
        var taskName = getTaskName(task);
        return new TaskStatisticId(topicStatistic, taskName);
    }

    public TaskStatistic findBy(TopicStatistic topicStatistic, Task task) {
        var taskStatisticId = toId(topicStatistic, task);
        return findBy(taskStatisticId);
    }

    public List<TaskStatistic> findAllBy(TopicStatistic topicStatistic) {
        try {
            return repository.findAllByTopicStatistic(topicStatistic);
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }

    public List<TaskStatistic> findAllBy(List<Topic> topics) {
        try {
            return repository.findAllBy(topics);
        } catch (Exception e) {
            throw new EntityServiceException();
        }
    }
}
