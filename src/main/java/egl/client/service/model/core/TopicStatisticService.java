package egl.client.service.model.core;

import java.util.HashMap;
import java.util.Map;

import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.repository.core.statistic.TopicStatisticRepository;
import egl.client.service.model.AbstractEntityService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public abstract class TopicStatisticService
        extends AbstractEntityService<TopicStatistic, TopicStatisticRepository> {

    @Data
    @RequiredArgsConstructor
    private static class TopicStatisticId {

        private final ProfileStatistic profileStatistic;
        private final Topic topic;
    }

    protected final Map<TopicStatisticId, TopicStatistic> topicStatisticCache;

    public TopicStatisticService(TopicStatisticRepository topicStatisticRepository) {
        super(topicStatisticRepository);
        this.topicStatisticCache = new HashMap<>();
    }

    private TopicStatistic findByInPersistence(TopicStatisticId topicStatisticId) {
        var topicStatistic = repository
                .findByProfileStatisticAndTopic(
                        topicStatisticId.getProfileStatistic(),
                        topicStatisticId.getTopic()
                );

        return topicStatistic.orElseGet(
                () -> save(
                        new TopicStatistic(
                                topicStatisticId.getProfileStatistic(),
                                topicStatisticId.getTopic()
                        )
                )
        );
    }

    public TopicStatistic findBy(ProfileStatistic profileStatistic, Topic topic) {
        var topicStatisticId = new TopicStatisticId(profileStatistic, topic);

        return topicStatisticCache.computeIfAbsent(
                topicStatisticId, this::findByInPersistence
        );
    }
}
