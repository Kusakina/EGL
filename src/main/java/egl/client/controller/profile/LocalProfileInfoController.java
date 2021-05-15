package egl.client.controller.profile;

import egl.client.controller.info.AbstractEntityInfoController;
import egl.client.model.profile.LocalProfile;
import egl.client.view.info.NameDescriptionInfoView;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class LocalProfileInfoController extends AbstractEntityInfoController<LocalProfile> {

    @FXML private NameDescriptionInfoView<LocalProfile> nameDescriptionInfoView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addInnerInfoViews(nameDescriptionInfoView);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        nameDescriptionInfoView.setPrefSize(parentWidth, parentHeight);
    }
}
