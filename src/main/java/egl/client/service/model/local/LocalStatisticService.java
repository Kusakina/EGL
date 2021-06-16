package egl.client.service.model.local;

import egl.client.model.core.topic.Topic;
import egl.client.service.model.core.AbstractStatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocalStatisticService
        extends AbstractStatisticService {

    public LocalStatisticService(
            LocalProfileService profileService,
            LocalProfileStatisticService profileStatisticService,
            LocalTopicStatisticService topicStatisticService,
            LocalTaskStatisticService taskStatisticService) {
        super(profileService, profileStatisticService, topicStatisticService, taskStatisticService);
    }

    public void removeAllBy(Topic topic) {
        topicStatisticService.findAllBy(topic)
            .forEach(topicStatistic -> {
                taskStatisticService.findAllBy(topicStatistic)
                        .forEach(taskStatisticService::remove);

                topicStatisticService.remove(topicStatistic);
            });
    }
}
