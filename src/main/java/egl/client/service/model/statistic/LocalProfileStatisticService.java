package egl.client.service.model.statistic;

import egl.client.repository.statistic.LocalProfileStatisticRepository;
import egl.client.service.model.AbstractEntityService;
import egl.core.model.profile.Profile;
import egl.core.model.statistic.ProfileStatistic;
import egl.core.model.statistic.TopicStatistic;
import egl.core.model.topic.Topic;
import org.springframework.stereotype.Service;

@Service
public class LocalProfileStatisticService extends AbstractEntityService<ProfileStatistic, LocalProfileStatisticRepository> {

    public LocalProfileStatisticService(LocalProfileStatisticRepository repository) {
        super(repository);
    }

    public ProfileStatistic findBy(Profile profile) {
        var profileStatistic = repository.findByProfile(profile);
        if (null == profileStatistic) {
            profileStatistic = new ProfileStatistic(profile);
            save(profileStatistic);
        }

        return profileStatistic;
    }

    public TopicStatistic findBy(Profile profile, Topic topic) {
        var profileStatistic = findBy(profile);
        return profileStatistic.getTopicStatisticFor(topic)
                .orElseGet(() -> addStatisticFor(profileStatistic, topic));
    }

    private TopicStatistic addStatisticFor(ProfileStatistic profileStatistic, Topic topic) {
        var topicStatistic = new TopicStatistic(topic);
        profileStatistic.getTopicStatistics().add(topicStatistic);
        save(profileStatistic);
        return topicStatistic;
    }
}
