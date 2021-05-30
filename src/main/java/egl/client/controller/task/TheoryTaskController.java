package egl.client.controller.task;

import egl.client.model.core.DatabaseEntity;
import egl.client.service.model.topic.LocalTopicInfoService;
import egl.client.service.model.topic.SpecificLocalTopicInfoService;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class TheoryTaskController<T extends DatabaseEntity> extends LocalTaskController<T> {

    public TheoryTaskController(LocalTopicInfoService localTopicInfoService,
                                SpecificLocalTopicInfoService<T> specificLocalTopicInfoService) {
        super(localTopicInfoService, specificLocalTopicInfoService);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        descriptionTextArea.setEditable(false);
    }

    @Override
    protected void prepareToStart() {
        descriptionTextArea.setText(localTopicInfo.getTheory().getText());
    }

    @Override
    protected void prepareToFinish() {
        result.registerAnswer(true);
    }
}
