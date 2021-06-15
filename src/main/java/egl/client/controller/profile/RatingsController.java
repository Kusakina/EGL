package egl.client.controller.profile;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import egl.client.controller.Controller;
import egl.client.model.core.topic.Topic;
import egl.client.service.model.core.StatisticService;
import egl.client.service.model.core.TopicByLocalService;
import egl.client.service.model.local.LocalTopicService;
import egl.client.service.model.local.LocalTopicTasksService;
import javafx.fxml.FXML;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RatingsController implements Controller {

    private final LocalTopicService localTopicService;
    private final TopicByLocalService topicByLocalService;

    private final LocalTopicTasksService localTopicTasksService;

    private final StatisticService statisticService;

    @FXML
    private RatingsView ratingsView;

    private boolean triedToUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.triedToUpdate = false;

        ratingsView.setFreshTopicsSupplier(this::getFreshTopics);
        ratingsView.setAllTaskStatisticsGetter(statisticService::findAllTaskStatisticsBy);
        ratingsView.setTopicTasksGetter(localTopicTasksService::findBy);
    }

    private List<Topic> getFreshTopics() {
        return localTopicService.findAll().stream()
                .map(topicByLocalService::findTopicByLocal)
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void prepareToShow() {
        refresh();
    }

    @Override
    public void refresh() {
        if (!triedToUpdate) {
            ratingsView.tryRefreshRatings();
            triedToUpdate = true;
        }
    }

    @Override
    public void prepareToClose() {

    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        ratingsView.setPrefSize(parentWidth, parentHeight);
    }
}
