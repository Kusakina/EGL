package egl.client.controller.profile.info;

import egl.client.service.FxmlService;
import egl.client.view.info.EntityInfoView;
import egl.client.view.info.NameDescriptionInfoView;
import egl.client.view.pane.CustomBorderPane;
import egl.core.model.profile.Profile;
import javafx.fxml.FXML;

public class ProfileInfoView extends CustomBorderPane implements EntityInfoView<Profile> {

    @FXML private NameDescriptionInfoView<Profile> nameDescriptionInfoView;

    public ProfileInfoView() {
        FxmlService.loadView(this, ProfileInfoView.class, true);
    }

    @Override
    public void initData(Profile profile, boolean isCreated) {
        nameDescriptionInfoView.initData(profile, isCreated);
    }

    @Override
    public void validateData() {
        nameDescriptionInfoView.validateData();
    }

    @Override
    public void fillData(Profile profile) {
        nameDescriptionInfoView.fillData(profile);
    }

    @Override
    public void setPrefSize(double prefWidth, double prefHeight) {
        nameDescriptionInfoView.setPrefSize(prefWidth, prefHeight);
    }
}
