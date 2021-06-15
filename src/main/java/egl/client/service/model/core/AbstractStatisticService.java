package egl.client.service.model.core;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import javafx.beans.property.Property;

public abstract class AbstractStatisticService implements StatisticService {

    public static final String NO_DATA = Result.NO_DATA;

    protected final ProfileService profileService;
    protected final ProfileStatisticService profileStatisticService;
    protected final TopicStatisticService topicStatisticService;
    protected final TaskStatisticService taskStatisticService;

    public AbstractStatisticService(
            ProfileService profileService,
            ProfileStatisticService profileStatisticService,
            TopicStatisticService topicStatisticService,
            TaskStatisticService taskStatisticService) {
        this.profileService = profileService;
        this.profileStatisticService = profileStatisticService;
        this.topicStatisticService = topicStatisticService;
        this.taskStatisticService = taskStatisticService;
    }

    @Override
    public Property<Profile> selectedProfileProperty() {
        return profileService.selectedProfileProperty();
    }

    @Override
    public Optional<Profile> getSelectedProfile() {
        return profileService.getSelectedProfile();
    }

    private Optional<ProfileStatistic> getSelectedProfileStatistic() {
        return getSelectedProfile()
                .map(profileStatisticService::findBy);
    }

    @Override
    public Optional<TopicStatistic> findBy(Topic topic) {
        return getSelectedProfileStatistic().map(profileStatistic ->
                topicStatisticService.findBy(profileStatistic, topic)
        );
    }

    @Override
    public TaskStatistic findBy(TopicStatistic topicStatistic, Task task) {
        return taskStatisticService.findBy(topicStatistic, task);
    }

    @Override
    public void update(TopicStatistic topicStatistic, Task task, Result result) {
        var taskStatistic = findBy(topicStatistic, task);
        if (taskStatistic.updateBy(result)) {
            taskStatisticService.save(taskStatistic);
        }
    }

    @Override
    public List<TaskStatistic> findAllTaskStatisticsBy(List<Long> topicIds) {
        return taskStatisticService.findAllBy(topicIds);
    }
}
