package egl.client.controller.profile;

import egl.client.controller.Controller;
import egl.client.model.profile.LocalProfile;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.LocalProfileService;
import egl.client.view.table.EntityServiceListView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class LocalProfilesController implements Controller {

    private final FxmlService fxmlService;
    private final LocalProfileService localProfileService;

    @FXML private EntityServiceListView<LocalProfile> localProfilesListView;
    @FXML private Button createProfileButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localProfilesListView.setService(localProfileService);
        localProfilesListView.setOnSelect(this::onSelect);
        localProfilesListView.setOnEdit(this::onEdit);

        createProfileButton.setOnAction(event -> onCreate());
    }

    private void onSelect(LocalProfile localProfile) {
        localProfileService.select(localProfile);
    }

    private void onCreate() {
        LocalProfile localProfile = new LocalProfile();
        onEdit(localProfile, true);
    }

    private void onEdit(LocalProfile localProfile) {
        onEdit(localProfile, false);
    }

    private void onEdit(LocalProfile localProfile, boolean isCreated) {
        var changed = fxmlService.showInfoDialog(
                LocalProfileInfoController.class,
                localProfile,
                "Новый пользователь", isCreated
        );

        if (changed) {
            localProfileService.save(localProfile);
            localProfilesListView.showItems();
        }
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
