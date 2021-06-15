package egl.client.service.model.global;

import egl.client.service.model.core.AbstractStatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GlobalStatisticService extends AbstractStatisticService {

    public GlobalStatisticService(GlobalProfileService profileService,
                                  GlobalProfileStatisticService profileStatisticService,
                                  GlobalTopicStatisticService topicStatisticService,
                                  GlobalTaskStatisticService taskStatisticService) {
        super(profileService, profileStatisticService, topicStatisticService, taskStatisticService);
    }
}
