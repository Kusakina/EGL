package egl.client.controller.profile.global;

import egl.client.controller.profile.StatisticsController;
import egl.client.service.FxmlService;
import org.springframework.stereotype.Component;

@Component
public class GlobalStatisticsController extends StatisticsController {

    public GlobalStatisticsController(FxmlService fxmlService) {
        super(fxmlService, GlobalProfileSelectController.class, GlobalRatingsController.class);
    }
}
