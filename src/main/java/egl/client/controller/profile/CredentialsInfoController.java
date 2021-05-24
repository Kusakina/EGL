package egl.client.controller.profile;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.info.AbstractEntityInfoController;
import egl.client.controller.profile.info.CredentialsLoginPasswordView;
import egl.client.controller.profile.info.CredentialsProfileInfoView;
import egl.core.model.profile.Credentials;
import javafx.fxml.FXML;

public class CredentialsInfoController extends AbstractEntityInfoController<Credentials> {

    @FXML private CredentialsProfileInfoView profileInfoView;
    @FXML private CredentialsLoginPasswordView loginPasswordView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addInnerInfoViews(profileInfoView, loginPasswordView);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        profileInfoView.setPrefSize(parentWidth, parentHeight * 0.5);
        profileInfoView.setPrefSize(parentWidth, parentHeight * 0.5);
    }
}
