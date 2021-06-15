package egl.client.service.model.local;

import java.util.Optional;

import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.service.model.core.AbstractStatisticService;
import egl.client.service.model.core.TopicStatisticByLocalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocalStatisticService
        extends AbstractStatisticService
        implements TopicStatisticByLocalService {

    public LocalStatisticService(
            LocalProfileService profileService,
            LocalProfileStatisticService profileStatisticService,
            LocalTopicStatisticService topicStatisticService,
            LocalTaskStatisticService taskStatisticService) {
        super(profileService, profileStatisticService, topicStatisticService, taskStatisticService);
    }

    @Override
    public Optional<TopicStatistic> findStatisticByLocal(Topic topic) {
        return findBy(topic);
    }
}
