package egl.client.controller.profile;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.info.AbstractEntityInfoController;
import egl.client.controller.profile.info.ProfileInfoView;
import egl.core.model.profile.Profile;
import javafx.fxml.FXML;

public class ProfileInfoController extends AbstractEntityInfoController<Profile> {

    @FXML private ProfileInfoView profileInfoView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addInnerInfoViews(profileInfoView);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        profileInfoView.setPrefSize(parentWidth, parentHeight);
    }
}
