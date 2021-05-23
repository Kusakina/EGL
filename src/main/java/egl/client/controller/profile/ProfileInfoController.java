package egl.client.controller.profile;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.info.AbstractEntityInfoController;
import egl.client.view.info.NameDescriptionInfoView;
import egl.core.model.profile.Profile;
import javafx.fxml.FXML;

public class ProfileInfoController extends AbstractEntityInfoController<Profile> {

    @FXML private NameDescriptionInfoView<Profile> nameDescriptionInfoView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addInnerInfoViews(nameDescriptionInfoView);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        nameDescriptionInfoView.setPrefSize(parentWidth, parentHeight);
    }
}
