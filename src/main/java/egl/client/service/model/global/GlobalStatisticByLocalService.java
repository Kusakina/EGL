package egl.client.service.model.global;

import java.util.Optional;

import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.service.model.core.TopicStatisticByLocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class GlobalStatisticByLocalService
        implements TopicStatisticByLocalService {

    private final GlobalStatisticService globalStatisticService;
    private final GlobalTopicService globalTopicService;

    @Override
    public Optional<TopicStatistic> findStatisticByLocal(Topic localTopic) {
        return globalTopicService.findTopicByLocal(localTopic)
                .flatMap(globalStatisticService::findBy);
    }
}
