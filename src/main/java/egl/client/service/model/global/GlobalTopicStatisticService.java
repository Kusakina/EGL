package egl.client.service.model.global;

import egl.client.repository.global.statistic.GlobalTopicStatisticRepository;
import egl.client.service.model.core.TopicStatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GlobalTopicStatisticService extends TopicStatisticService {

    public GlobalTopicStatisticService(GlobalTopicStatisticRepository topicStatisticRepository) {
        super(topicStatisticRepository);
    }
}
