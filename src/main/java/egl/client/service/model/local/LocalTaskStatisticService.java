package egl.client.service.model.local;

import egl.client.repository.local.statistic.LocalTaskStatisticRepository;
import egl.client.service.model.core.TaskStatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocalTaskStatisticService extends TaskStatisticService {

    public LocalTaskStatisticService(LocalTaskStatisticRepository taskStatisticRepository) {
        super(taskStatisticRepository);
    }
}
