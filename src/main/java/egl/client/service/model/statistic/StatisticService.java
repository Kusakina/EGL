package egl.client.service.model.statistic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.repository.core.statistic.ProfileStatisticRepository;
import egl.client.service.model.profile.ProfileService;
import javafx.beans.property.Property;

public abstract class StatisticService {

    public static final String NO_DATA = "Нет данных";

    protected final ProfileService profileService;
    protected final ProfileStatisticRepository profileStatisticRepository;

    protected final Map<Profile, ProfileStatistic> profileToStatisticCache;

    public StatisticService(
            ProfileService profileService,
            ProfileStatisticRepository profileStatisticRepository) {
        this.profileService = profileService;
        this.profileStatisticRepository = profileStatisticRepository;
        this.profileToStatisticCache = new HashMap<>();
    }

    private TopicStatistic memorize(TopicStatistic global) {
        var memorized = new TopicStatistic();
        memorized.setId(global.getId());
        memorized.setTopic(global.getTopic());
        memorized.getTaskStatistics().addAll(
                global.getTaskStatistics()
        );
        return memorized;
    }

    private ProfileStatistic memorize(ProfileStatistic global) {
        if (global == null) return new ProfileStatistic();

        var memorized = new ProfileStatistic(global.getProfile());
        memorized.setId(global.getId());
        memorized.getTopicStatistics().addAll(
                global.getTopicStatistics().stream()
                        .map(this::memorize)
                        .collect(Collectors.toSet())
        );

        memorized.getTopicStatistics().forEach(
                topicStatistic -> topicStatistic.setProfileStatistic(memorized)
        );

        return memorized;
    }

    private ProfileStatistic findBy(Profile profile) {
        var profileStatistic = profileStatisticRepository.findByProfile(profile);
        return memorize(profileStatistic);
    }

    public Property<Profile> selectedProfileProperty() {
        return profileService.selectedProfileProperty();
    }

    public Optional<ProfileStatistic> getSelectedProfileStatistic() {
        return Optional.ofNullable(profileService.getSelectedProfile())
                .map(profile -> profileToStatisticCache.computeIfAbsent(profile, this::findBy));
    }

    public ProfileStatistic save(ProfileStatistic profileStatistic) {
        var saved = profileStatisticRepository.save(profileStatistic);
        profileStatistic = memorize(saved);
        profileToStatisticCache.put(
                profileStatistic.getProfile(),
                profileStatistic
        );
        return profileStatistic;
    }

    public Optional<TopicStatistic> findBy(Topic topic) {
        return getSelectedProfileStatistic().map(profileStatistic ->
            profileStatistic.getTopicStatisticFor(topic)
            .orElseGet(() -> addStatisticFor(profileStatistic, topic))
        );
    }

    private TopicStatistic addStatisticFor(ProfileStatistic profileStatistic, Topic topic) {
        var topicStatistic = new TopicStatistic(profileStatistic, topic);
        profileStatistic.getTopicStatistics().add(topicStatistic);
        return save(profileStatistic).getTopicStatisticFor(topic).orElseThrow();
    }

    public void save(TopicStatistic topicStatistic) {
        save(topicStatistic.getProfileStatistic());
    }
}
