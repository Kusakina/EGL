package egl.client.service.model.statistic;

import egl.client.repository.global.statistic.GlobalProfileStatisticRepository;
import egl.client.service.model.profile.GlobalProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GlobalStatisticService extends StatisticService {

    public GlobalStatisticService(
            GlobalProfileService profileService,
            GlobalProfileStatisticRepository profileStatisticRepository) {
        super(profileService, profileStatisticRepository);
    }
}
