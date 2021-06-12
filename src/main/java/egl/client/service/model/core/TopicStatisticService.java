package egl.client.service.model.core;

import java.util.Optional;

import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.repository.core.statistic.TopicStatisticRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public abstract class TopicStatisticService
        extends CachedEntityService<
            TopicStatistic,
            TopicStatisticRepository,
            TopicStatisticService.TopicStatisticId
        > {

    @Data
    @RequiredArgsConstructor
    static class TopicStatisticId implements EntityId {

        private final ProfileStatistic profileStatistic;
        private final Topic topic;
    }

    protected TopicStatisticService(TopicStatisticRepository repository) {
        super(repository);
    }

    @Override
    protected Optional<TopicStatistic> findById(TopicStatisticId topicStatisticId) {
        return repository.findByProfileStatisticAndTopic(
                        topicStatisticId.getProfileStatistic(),
                        topicStatisticId.getTopic()
                );
    }

    @Override
    protected TopicStatistic createWith(TopicStatisticId topicStatisticId) {
        return new TopicStatistic(
                topicStatisticId.getProfileStatistic(),
                topicStatisticId.getTopic()
        );
    }

    @Override
    protected TopicStatisticId getIdOf(TopicStatistic topicStatistic) {
        return new TopicStatisticId(topicStatistic.getProfileStatistic(), topicStatistic.getTopic());
    }

    public TopicStatistic findBy(ProfileStatistic profileStatistic, Topic topic) {
        var topicStatisticId = new TopicStatisticId(profileStatistic, topic);
        return findBy(topicStatisticId);
    }

    public Optional<TopicStatistic> tryFindBy(ProfileStatistic profileStatistic, Topic topic) {
        var topicStatisticId = new TopicStatisticId(profileStatistic, topic);
        return tryFindBy(topicStatisticId);
    }
}
