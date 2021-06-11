package egl.client.service.model.core;

import java.util.HashMap;
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
import egl.client.repository.core.statistic.TopicStatisticRepository;
import egl.client.service.model.AbstractEntityService;
import javafx.beans.property.Property;

public abstract class StatisticService
        extends AbstractEntityService<ProfileStatistic, ProfileStatisticRepository> {

    public static final String NO_DATA = Result.NO_DATA;

    protected final ProfileService profileService;
    protected final ProfileStatisticRepository profileStatisticRepository;
    protected final TopicStatisticRepository topicStatisticRepository;
    protected final TaskStatisticService taskStatisticService;

    protected final Map<Profile, ProfileStatistic> profileToStatisticCache;

    public StatisticService(
            ProfileService profileService,
            ProfileStatisticRepository profileStatisticRepository,
            TopicStatisticRepository topicStatisticRepository,
            TaskStatisticService taskStatisticService) {
        super(profileStatisticRepository);
        this.profileService = profileService;
        this.profileStatisticRepository = profileStatisticRepository;
        this.topicStatisticRepository = topicStatisticRepository;
        this.taskStatisticService = taskStatisticService;
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
