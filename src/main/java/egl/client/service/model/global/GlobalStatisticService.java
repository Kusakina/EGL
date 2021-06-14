package egl.client.service.model.global;

import java.util.List;
import java.util.Optional;

import egl.client.model.core.statistic.ProfileStatistic;
import egl.client.model.core.statistic.TaskStatistic;
import egl.client.model.core.task.Task;
import egl.client.model.core.topic.Topic;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.core.StatisticService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GlobalStatisticService extends StatisticService {

    public GlobalStatisticService(GlobalProfileService profileService,
                                  GlobalProfileStatisticService profileStatisticService,
                                  GlobalTopicStatisticService topicStatisticService,
                                  GlobalTaskStatisticService taskStatisticService) {
        super(profileService, profileStatisticService, topicStatisticService, taskStatisticService);
    }

    public List<ProfileStatistic> findAll() throws EntityServiceException {
        return profileStatisticService.findAll();
    }

    public Optional<TaskStatistic> tryFindBy(ProfileStatistic profileStatistic, Topic globalTopic, Task task) {
        return topicStatisticService.tryFindBy(profileStatistic, globalTopic)
                .flatMap(topicStatistic -> taskStatisticService.tryFindBy(topicStatistic, task));
    }
}
