package egl.client.service.model.local;

import egl.client.service.model.core.AbstractStatisticService;
import egl.client.service.model.core.StatisticFindService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocalStatisticService extends AbstractStatisticService implements StatisticFindService {

    public LocalStatisticService(
            LocalProfileService profileService,
            LocalProfileStatisticService profileStatisticService,
            LocalTopicStatisticService topicStatisticService,
            LocalTaskStatisticService taskStatisticService) {
        super(profileService, profileStatisticService, topicStatisticService, taskStatisticService);
    }
}
