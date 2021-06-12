package egl.client.service.model.local;

import egl.client.repository.local.statistic.LocalProfileStatisticRepository;
import egl.client.service.model.core.ProfileStatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocalProfileStatisticService extends ProfileStatisticService {

    public LocalProfileStatisticService(LocalProfileStatisticRepository profileStatisticRepository) {
        super(profileStatisticRepository);
    }
}
