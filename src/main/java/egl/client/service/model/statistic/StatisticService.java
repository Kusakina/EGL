package egl.client.service.model.statistic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.Result;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.repository.core.statistic.ProfileStatisticRepository;
import egl.client.repository.core.statistic.TaskStatisticRepository;
import egl.client.repository.core.statistic.TopicStatisticRepository;
import egl.client.service.model.AbstractEntityService;
import egl.client.service.model.profile.ProfileService;
import javafx.beans.property.Property;

public abstract class StatisticService
        extends AbstractEntityService<ProfileStatistic, ProfileStatisticRepository> {

    public static final String NO_DATA = Result.NO_DATA;

    protected final ProfileService profileService;
    protected final ProfileStatisticRepository profileStatisticRepository;
    protected final TopicStatisticRepository topicStatisticRepository;
    protected final TaskStatisticRepository taskStatisticRepository;

    protected final Map<Profile, ProfileStatistic> profileToStatisticCache;

    public StatisticService(
            ProfileService profileService,
            ProfileStatisticRepository profileStatisticRepository,
            TopicStatisticRepository topicStatisticRepository,
            TaskStatisticRepository taskStatisticRepository) {
        super(profileStatisticRepository);
        this.profileService = profileService;
        this.profileStatisticRepository = profileStatisticRepository;
        this.topicStatisticRepository = topicStatisticRepository;
        this.taskStatisticRepository = taskStatisticRepository;
        this.profileToStatisticCache = new HashMap<>();
    }

    private void memorize(TopicStatistic global) {
        var memorizedTaskStatistics = new HashSet<>(global.getTaskStatistics());
        global.setTaskStatistics(memorizedTaskStatistics);
    }

    private void memorize(ProfileStatistic global) {
        var memorizedTopicStatistics = new HashSet<>(global.getTopicStatistics());
        global.setTopicStatistics(memorizedTopicStatistics);

        memorizedTopicStatistics.forEach(this::memorize);
    }

    private ProfileStatistic findBy(Profile profile) {
        var profileStatistic = profileStatisticRepository.findByProfile(profile);
        if (null == profileStatistic) {
            profileStatistic = profileStatisticRepository.save(new ProfileStatistic(profile));
        }

        memorize(profileStatistic);

        return profileStatistic;
    }

    public Property<Profile> selectedProfileProperty() {
        return profileService.selectedProfileProperty();
    }

    public Optional<ProfileStatistic> getSelectedProfileStatistic() {
        return Optional.ofNullable(profileService.getSelectedProfile())
                .map(profile -> profileToStatisticCache.computeIfAbsent(profile, this::findBy));
    }

    public Optional<TopicStatistic> findBy(Topic topic) {
        return getSelectedProfileStatistic().map(profileStatistic ->
            profileStatistic.getTopicStatisticFor(topic)
            .orElseGet(() -> addStatisticFor(profileStatistic, topic))
        );
    }

    private TopicStatistic addStatisticFor(ProfileStatistic profileStatistic, Topic topic) {
        var topicStatistic = new TopicStatistic(profileStatistic, topic);
        topicStatistic = topicStatisticRepository.save(topicStatistic);
        profileStatistic.getTopicStatistics().add(topicStatistic);
        return topicStatistic;
    }

    public Optional<TaskStatistic> findBy(Topic topic, Task task) {
        return findBy(topic).map(topicStatistic ->
            topicStatistic.getTaskStatisticFor(task)
            .orElseGet(() -> addStatisticFor(topicStatistic, task))
        );
    }

    private TaskStatistic addStatisticFor(TopicStatistic topicStatistic, Task task) {
        var taskStatistic = save(new TaskStatistic(task, Result.NONE));
        topicStatistic.getTaskStatistics().add(taskStatistic);
        return taskStatistic;
    }

    private TaskStatistic save(TaskStatistic taskStatistic) {
        return taskStatisticRepository.save(taskStatistic);
    }

    public void update(Topic topic, Task task, Result result) {
        findBy(topic, task).ifPresent(taskStatistic -> {
            if (taskStatistic.updateBy(result)) {
                save(taskStatistic);
            }
        });
    }
}
