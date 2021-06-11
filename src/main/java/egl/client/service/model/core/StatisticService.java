package egl.client.service.model.core;

import java.util.HashMap;
import java.util.List;
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

    private ProfileStatistic findBy(Profile profile) {
        var profileStatistic = profileStatisticRepository.findByProfile(profile);
        if (null == profileStatistic) {
            profileStatistic = profileStatisticRepository.save(new ProfileStatistic(profile));
        }

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
        return getSelectedProfileStatistic().map(profileStatistic -> findBy(profileStatistic, topic));
    }

    public TopicStatistic findBy(ProfileStatistic profileStatistic, Topic topic) {
        return topicStatisticRepository
                .findByProfileStatisticAndTopic(profileStatistic, topic)
                .orElseGet(() -> addStatisticFor(profileStatistic, topic));
    }

    private TopicStatistic addStatisticFor(ProfileStatistic profileStatistic, Topic topic) {
        var topicStatistic = new TopicStatistic(profileStatistic, topic);
        return topicStatisticRepository.save(topicStatistic);
    }

    public Optional<TaskStatistic> findBy(Topic topic, Task task) {
        return getSelectedProfileStatistic()
                .map(profileStatistic -> findBy(profileStatistic, topic, task));
    }

    public TaskStatistic findBy(ProfileStatistic profileStatistic, Topic topic, Task task) {
        var topicStatistic = findBy(profileStatistic, topic);
        return taskStatisticRepository
                .findByTopicStatisticAndTaskName(topicStatistic, task.getName())
                .orElseGet(() -> addStatisticFor(topicStatistic, task));
    }

    private TaskStatistic addStatisticFor(TopicStatistic topicStatistic, Task task) {
        var taskStatistic = new TaskStatistic(topicStatistic, task, Result.NONE);
        return taskStatisticRepository.save(taskStatistic);
    }

    public void update(Topic topic, Task task, Result result) {
        findBy(topic, task).ifPresent(taskStatistic -> {
            if (taskStatistic.updateBy(result)) {
                taskStatisticRepository.save(taskStatistic);
            }
        });
    }

    public List<TaskStatistic> findAllBy(TopicStatistic topicStatistic) {
        return taskStatisticRepository.findAllByTopicStatistic(topicStatistic);
    }
}
