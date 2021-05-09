package egl.client.controller.topic;

import egl.client.controller.EntitiesListController;
import egl.client.controller.WindowController;
import egl.client.model.topic.LocalTopic;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityService;
import javafx.event.ActionEvent;

abstract class LocalTopicsListController<
        T extends LocalTopic,
        ServiceType extends EntityService<T>
        > extends EntitiesListController<T, ServiceType> {

    protected LocalTopicsListController(FxmlService fxmlService, ServiceType service) {
        super(fxmlService, service);
    }

    protected void onSelect(ActionEvent event, T topic) {
        var localTopicRoot = fxmlService.load(TopicTasksController.class);

        var topicController = (TopicTasksController) localTopicRoot.getController();
        topicController.setContext(topic);

        fxmlService.showStage(
                localTopicRoot, topic.getName(), WindowController.CLOSE
        );
    }
}
