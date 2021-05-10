package egl.client.controller.profile;

import egl.client.controller.Controller;
import egl.client.model.profile.LocalProfile;
import egl.client.service.model.profile.LocalProfileService;
import egl.client.view.table.EntityServiceListView;
import javafx.fxml.FXML;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class LocalProfilesController implements Controller {

    private final LocalProfileService localProfileService;

    @FXML private EntityServiceListView<LocalProfile> localProfilesListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localProfilesListView.setService(localProfileService);
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        localProfilesListView.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    public void prepareToShow() {
        localProfilesListView.showItems();
    }

    @Override
    public void prepareToClose() {

    }
}
