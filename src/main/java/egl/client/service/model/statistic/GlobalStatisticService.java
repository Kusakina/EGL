package egl.client.service.model.statistic;

import java.util.Optional;

import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.topic.Topic;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.repository.global.statistic.GlobalProfileStatisticRepository;
import egl.client.service.model.profile.GlobalProfileService;
import egl.client.service.model.topic.GlobalTopicInfoService;
import egl.client.service.model.topic.GlobalTopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GlobalStatisticService extends StatisticService {

    private final GlobalTopicService globalTopicService;
    private final GlobalTopicInfoService globalTopicInfoService;

    public GlobalStatisticService(GlobalProfileService profileService,
                                  GlobalProfileStatisticRepository profileStatisticRepository,
                                  GlobalTopicService globalTopicService,
                                  GlobalTopicInfoService globalTopicInfoService) {
        super(profileService, profileStatisticRepository);
        this.globalTopicService = globalTopicService;
        this.globalTopicInfoService = globalTopicInfoService;
    }

    @Override
    public Optional<TopicStatistic> findBy(Topic localTopic) {
        return globalTopicService.findByLocal(localTopic)
                .flatMap(super::findBy);
    }

    public long registerTopic(Topic topic, int localHashCode) {
        var author = profileService.getSelectedProfile();
        if (null == author) {
            return LocalTopicInfo.NO_GLOBAL_ID;
        }

        var globalTopicInfo = globalTopicInfoService.save(
                topic, localHashCode, author
        );

        return globalTopicInfo.getTopic().getId();
    }
}
