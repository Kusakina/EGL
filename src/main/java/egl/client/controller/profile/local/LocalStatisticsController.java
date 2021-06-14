package egl.client.controller.profile.local;

import egl.client.controller.profile.StatisticsController;
import egl.client.service.FxmlService;
import org.springframework.stereotype.Component;

@Component
public class LocalStatisticsController extends StatisticsController {

    public LocalStatisticsController(FxmlService fxmlService) {
        super(fxmlService, LocalProfileSelectController.class, LocalRatingsController.class);
    }
}
