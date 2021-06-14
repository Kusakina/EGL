package egl.client.controller.profile.global;

import egl.client.controller.profile.RatingsController;
import egl.client.service.model.global.GlobalStatisticService;
import egl.client.service.model.global.GlobalTopicService;
import egl.client.service.model.local.LocalTopicService;
import egl.client.service.model.local.LocalTopicTasksService;
import org.springframework.stereotype.Component;

@Component
class GlobalRatingsController extends RatingsController {

    public GlobalRatingsController(LocalTopicService localTopicService,
                                   GlobalTopicService topicByLocalService,
                                   LocalTopicTasksService localTopicTasksService,
                                   GlobalStatisticService statisticService) {
        super(localTopicService,
            topicByLocalService,
            localTopicTasksService,
            statisticService
        );
    }
}
