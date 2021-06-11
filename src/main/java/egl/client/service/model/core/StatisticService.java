package egl.client.service.model.core;

import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import javafx.beans.property.Property;

public abstract class StatisticService {

    public static final String NO_DATA = Result.NO_DATA;

    protected final ProfileService profileService;
    protected final ProfileStatisticService profileStatisticService;
    protected final TopicStatisticService topicStatisticService;
    protected final TaskStatisticService taskStatisticService;

    public StatisticService(
            ProfileService profileService,
            ProfileStatisticService profileStatisticService,
            TopicStatisticService topicStatisticService,
            TaskStatisticService taskStatisticService) {
        this.profileService = profileService;
        this.profileStatisticService = profileStatisticService;
        this.topicStatisticService = topicStatisticService;
        this.taskStatisticService = taskStatisticService;
    }

    public Property<Profile> selectedProfileProperty() {
        return profileService.selectedProfileProperty();
    }

    public Optional<ProfileStatistic> getSelectedProfileStatistic() {
        return profileService.getSelectedProfile()
                .map(profileStatisticService::findBy);
    }

    public Optional<TopicStatistic> findBy(Topic topic) {
        return getSelectedProfileStatistic().map(profileStatistic ->
                topicStatisticService.findBy(profileStatistic, topic)
        );
    }

    public Optional<TaskStatistic> findBy(Topic topic, Task task) {
        return findBy(topic)
                .map(topicStatistic -> findBy(topicStatistic, task));
    }

    public TaskStatistic findBy(TopicStatistic topicStatistic, Task task) {
        return taskStatisticService.findBy(topicStatistic, task);
    }

    public void update(Topic topic, Task task, Result result) {
        findBy(topic).ifPresent(topicStatistic -> {
            var taskStatistic = taskStatisticService.findBy(topicStatistic, task);
            if (taskStatistic.updateBy(result)) {
                taskStatisticService.save(taskStatistic);
            }
        });
    }
}
