package egl.client.service.model.local;

import egl.client.service.model.core.StatisticService;
import egl.client.service.model.core.StatisticServiceHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocalStatisticService extends StatisticService implements StatisticServiceHolder {

    public LocalStatisticService(
            LocalProfileService profileService,
            LocalProfileStatisticService profileStatisticService,
            LocalTopicStatisticService topicStatisticService,
            LocalTaskStatisticService taskStatisticService) {
        super(profileService, profileStatisticService, topicStatisticService, taskStatisticService);
    }
}