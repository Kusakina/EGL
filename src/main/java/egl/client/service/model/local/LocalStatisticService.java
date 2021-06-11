package egl.client.service.model.local;

import egl.client.repository.local.statistic.LocalProfileStatisticRepository;
import egl.client.repository.local.statistic.LocalTaskStatisticRepository;
import egl.client.repository.local.statistic.LocalTopicStatisticRepository;
import egl.client.service.model.core.StatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocalStatisticService extends StatisticService {

    public LocalStatisticService(
            LocalProfileService profileService,
            LocalProfileStatisticRepository profileStatisticRepository,
            LocalTopicStatisticRepository topicStatisticRepository,
            LocalTaskStatisticRepository taskStatisticRepository) {
        super(profileService,
                profileStatisticRepository,
                topicStatisticRepository,
                taskStatisticRepository);
    }
}
