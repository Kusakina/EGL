package egl.client.service.model.statistic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.repository.core.statistic.ProfileStatisticRepository;
import egl.client.repository.core.statistic.TopicStatisticRepository;
import egl.client.service.model.profile.ProfileService;
import javafx.beans.property.Property;

public abstract class StatisticService {

    public static final String NO_DATA = "Нет данных";

    protected final ProfileService profileService;
    protected final ProfileStatisticRepository profileStatisticRepository;
    protected final TopicStatisticRepository topicStatisticRepository;

    protected final Map<Profile, ProfileStatistic> profileToStatisticCache;

    public StatisticService(
            ProfileService profileService,
            ProfileStatisticRepository profileStatisticRepository,
            TopicStatisticRepository topicStatisticRepository) {
        this.profileService = profileService;
        this.profileStatisticRepository = profileStatisticRepository;
        this.topicStatisticRepository = topicStatisticRepository;
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
            profileStatistic = save(new ProfileStatistic(profile));
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

    public ProfileStatistic save(ProfileStatistic profileStatistic) {
        return profileStatisticRepository.save(profileStatistic);
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

    public void save(TopicStatistic topicStatistic) {
        topicStatisticRepository.save(topicStatistic);
    }
}
