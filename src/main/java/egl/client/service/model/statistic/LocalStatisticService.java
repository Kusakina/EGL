package egl.client.service.model.statistic;

import egl.client.repository.statistic.LocalProfileStatisticRepository;
import egl.core.model.profile.Profile;
import egl.core.model.statistic.ProfileStatistic;
import egl.core.model.statistic.TopicStatistic;
import egl.core.model.topic.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class LocalStatisticService {

    private final LocalProfileStatisticRepository localProfileStatisticRepository;

    public ProfileStatistic findBy(Profile profile) {
        var profileStatistic = localProfileStatisticRepository.findByProfile(profile);
        if (null == profileStatistic) {
            profileStatistic = new ProfileStatistic(profile);
            save(profileStatistic);
        }

        return profileStatistic;
    }

    public void save(ProfileStatistic profileStatistic) {
        localProfileStatisticRepository.save(profileStatistic);
    }

    public TopicStatistic findBy(Profile profile, Topic topic) {
        var profileStatistic = findBy(profile);
        return profileStatistic.getTopicStatisticFor(topic)
                .orElseGet(() -> addStatisticFor(profileStatistic, topic));
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
