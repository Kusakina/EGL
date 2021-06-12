package egl.client.service.model.local;

import java.util.Optional;

import egl.client.model.core.topic.Topic;
import egl.client.service.model.core.StatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocalStatisticService extends StatisticService {

    public LocalStatisticService(
            LocalProfileService profileService,
            LocalProfileStatisticService profileStatisticService,
            LocalTopicStatisticService topicStatisticService,
            LocalTaskStatisticService taskStatisticService) {
        super(profileService, profileStatisticService, topicStatisticService, taskStatisticService);
    }

    @Override
    public Optional<Topic> fromLocal(Topic topic) {
        return Optional.of(topic);
    }
}
