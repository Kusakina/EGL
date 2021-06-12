package egl.client.service.model.global;

import egl.client.repository.global.statistic.GlobalProfileStatisticRepository;
import egl.client.service.model.core.ProfileStatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GlobalProfileStatisticService extends ProfileStatisticService {

    public GlobalProfileStatisticService(GlobalProfileStatisticRepository profileStatisticRepository) {
        super(profileStatisticRepository);
    }
}
