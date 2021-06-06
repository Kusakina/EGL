package egl.client.service.model.statistic;

import java.util.Optional;

import egl.client.repository.local.statistic.LocalProfileStatisticRepository;
import egl.client.model.core.profile.Profile;
import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.service.model.profile.LocalProfileService;
import javafx.beans.property.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class LocalStatisticService {

    private final LocalProfileService localProfileService;
    private final LocalProfileStatisticRepository localProfileStatisticRepository;

    private ProfileStatistic findBy(Profile profile) {
        var profileStatistic = localProfileStatisticRepository.findByProfile(profile);
        if (null == profileStatistic) {
            profileStatistic = new ProfileStatistic(profile);
            save(profileStatistic);
        }

        return profileStatistic;
    }

    public Optional<ProfileStatistic> getSelectedProfileStatistic() {
        var profile = Optional.ofNullable(localProfileService.getSelectedProfile());
        return profile.map(this::findBy);
    }

    public void save(ProfileStatistic profileStatistic) {
        localProfileStatisticRepository.save(profileStatistic);
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
