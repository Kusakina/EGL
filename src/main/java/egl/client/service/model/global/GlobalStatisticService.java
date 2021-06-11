package egl.client.service.model.global;

import java.util.Optional;

import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.statistic.TopicStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.model.local.topic.LocalTopicInfo;
import egl.client.service.model.core.StatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GlobalStatisticService extends StatisticService {

    private final GlobalTopicService globalTopicService;

    public GlobalStatisticService(GlobalProfileService profileService,
                                  GlobalProfileStatisticService profileStatisticService,
                                  GlobalTopicStatisticService topicStatisticService,
                                  GlobalTaskStatisticService taskStatisticService,
                                  GlobalTopicService globalTopicService) {
        super(
            profileService,
            profileStatisticService,
            topicStatisticService,
            taskStatisticService
        );
        this.globalTopicService = globalTopicService;
    }

    @Override
    public Optional<TopicStatistic> findBy(Topic localTopic) {
        return globalTopicService.findByLocal(localTopic)
                .flatMap(super::findBy);
    }

    @Override
    public Optional<TaskStatistic> findBy(Topic localTopic, Task task) {
        return globalTopicService.findByLocal(localTopic)
                .flatMap(globalTopic -> super.findBy(globalTopic, task));
    }

    public void registerTopic(LocalTopicInfo localTopicInfo) {
        profileService.getSelectedProfile().ifPresent(author ->
                globalTopicService.registerTopic(localTopicInfo, author)
        );
    }
}
