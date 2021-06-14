package egl.client.controller.profile.local;

import egl.client.controller.profile.RatingsController;
import egl.client.service.model.local.LocalStatisticService;
import egl.client.service.model.local.LocalTopicService;
import egl.client.service.model.local.LocalTopicTasksService;
import org.springframework.stereotype.Component;

@Component
class LocalRatingsController extends RatingsController {

    public LocalRatingsController(LocalTopicService localTopicService,
                                  LocalTopicTasksService localTopicTasksService,
                                  LocalStatisticService statisticService) {
        super(localTopicService,
            localTopicService,
            localTopicTasksService,
            statisticService
        );
    }
}
