package egl.client.controller.task;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.model.core.DatabaseEntity;
import egl.client.service.model.local.LocalTopicInfoService;
import egl.client.service.model.local.SpecificLocalTopicInfoService;

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
