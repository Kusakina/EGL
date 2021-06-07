package egl.client.service.model.statistic;

import egl.client.repository.local.statistic.LocalProfileStatisticRepository;
import egl.client.repository.local.statistic.LocalTopicStatisticRepository;
import egl.client.service.model.profile.LocalProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocalStatisticService extends StatisticService {

    public LocalStatisticService(
            LocalProfileService profileService,
            LocalProfileStatisticRepository profileStatisticRepository,
            LocalTopicStatisticRepository topicStatisticRepository) {
        super(profileService, profileStatisticRepository, topicStatisticRepository);
    }
}
