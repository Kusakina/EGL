package egl.client.service.model.global;

import egl.client.repository.global.statistic.GlobalTaskStatisticRepository;
import egl.client.service.model.core.TaskStatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GlobalTaskStatisticService extends TaskStatisticService {

    public GlobalTaskStatisticService(GlobalTaskStatisticRepository taskStatisticRepository) {
        super(taskStatisticRepository);
    }
}
