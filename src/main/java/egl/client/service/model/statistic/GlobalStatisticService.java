package egl.client.service.model.statistic;

import java.util.Optional;

import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.repository.global.statistic.GlobalProfileStatisticRepository;
import egl.client.service.model.profile.GlobalProfileService;
import egl.client.service.model.topic.GlobalTopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GlobalStatisticService extends StatisticService {

    private final GlobalTopicService globalTopicService;

    public GlobalStatisticService(
            GlobalProfileService profileService,
            GlobalProfileStatisticRepository profileStatisticRepository,
            GlobalTopicService globalTopicService) {
        super(profileService, profileStatisticRepository);
        this.globalTopicService = globalTopicService;
    }

    @Override
    public Optional<TopicStatistic> findBy(Topic localTopic) {
        return globalTopicService.findByLocal(localTopic)
                .flatMap(super::findBy);
    }
}
