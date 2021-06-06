package egl.client.service.model.statistic;

import java.util.Optional;

import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.repository.core.statistic.ProfileStatisticRepository;
import egl.client.service.model.profile.ProfileService;
import javafx.beans.property.Property;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class StatisticService {

    protected final ProfileService profileService;
    protected final ProfileStatisticRepository profileStatisticRepository;

    private ProfileStatistic findBy(Profile profile) {
        var profileStatistic = profileStatisticRepository.findByProfile(profile);
        if (null == profileStatistic) {
            profileStatistic = new ProfileStatistic(profile);
            save(profileStatistic);
        }

        return profileStatistic;
    }

    public Property<Profile> selectedProfileProperty() {
        return profileService.selectedProfileProperty();
    }

    public Optional<ProfileStatistic> getSelectedProfileStatistic() {
        var profile = Optional.ofNullable(profileService.getSelectedProfile());
        return profile.map(this::findBy);
    }

    public void save(ProfileStatistic profileStatistic) {
        profileStatisticRepository.save(profileStatistic);
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
        save(profileStatistic);
        return profileStatistic.getTopicStatisticFor(topic).orElseThrow();
    }

    public void save(TopicStatistic topicStatistic) {
        save(topicStatistic.getProfileStatistic());
    }
}
