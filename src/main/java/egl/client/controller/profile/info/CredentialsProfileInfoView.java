package egl.client.controller.profile.info;

import egl.client.service.FxmlService;
import egl.client.view.info.EntityInfoView;
import egl.client.view.pane.CustomBorderPane;
import egl.client.model.core.profile.Credentials;
import javafx.fxml.FXML;

public class CredentialsProfileInfoView extends CustomBorderPane implements EntityInfoView<Credentials> {

    @FXML private ProfileInfoView profileInfoView;

    public CredentialsProfileInfoView() {
        FxmlService.loadView(this, CredentialsProfileInfoView.class, true);
    }

    @Override
    public void initData(Credentials credentials, boolean isCreated) {
        profileInfoView.initData(credentials.getProfile(), isCreated);
    }

    @Override
    public void validateData() {
        profileInfoView.validateData();
    }

    @Override
    public void fillData(Credentials credentials) {
        profileInfoView.fillData(credentials.getProfile());
    }

    @Override
    public void setPrefSize(double prefWidth, double prefHeight) {
        super.setPrefSize(prefWidth, prefHeight);
        profileInfoView.setPrefSize(prefWidth, prefHeight);
    }
}
