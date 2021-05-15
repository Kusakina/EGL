package egl.client.controller.profile;

import egl.client.controller.info.AbstractEntityInfoController;
import egl.client.model.profile.LocalProfile;
import egl.client.view.info.NameDescriptionInfoView;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
public class LocalProfileInfoController extends AbstractEntityInfoController<LocalProfile> {

    @FXML private NameDescriptionInfoView<LocalProfile> nameDescriptionInfoView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void validateData() {
        nameDescriptionInfoView.validateData();
    }

    @Override
    public void fillData() {
        nameDescriptionInfoView.fillData(entity);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        nameDescriptionInfoView.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    public void prepareToShow() {
        nameDescriptionInfoView.initData(entity, isCreated);
    }

    @Override
    public void prepareToClose() {

    }
}
