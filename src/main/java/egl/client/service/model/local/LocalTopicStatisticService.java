package egl.client.service.model.local;

import egl.client.repository.local.statistic.LocalTopicStatisticRepository;
import egl.client.service.model.core.TopicStatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocalTopicStatisticService extends TopicStatisticService {

    public LocalTopicStatisticService(LocalTopicStatisticRepository topicStatisticRepository) {
        super(topicStatisticRepository);
    }
}
