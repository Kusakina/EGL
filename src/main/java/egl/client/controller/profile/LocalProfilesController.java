package egl.client.controller.profile;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.model.core.profile.Profile;
import egl.client.service.FxmlService;
import egl.client.service.model.local.LocalProfileService;
import egl.client.view.table.list.InfoSelectEditRemoveListView;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import org.springframework.stereotype.Component;

@Component
public class LocalProfilesController extends ProfileSelectController {

    @FXML private InfoSelectEditRemoveListView<Profile> localProfilesListView;

    public LocalProfilesController(
            FxmlService fxmlService,
            LocalProfileService profileService) {
        super(fxmlService, profileService);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localProfilesListView.setService(profileService);
        localProfilesListView.setOnSelect(this::onSelect);
        localProfilesListView.setOnEdit(this::onEdit);

        localProfilesListView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Profile profile, boolean empty) {
                super.updateItem(profile, empty);

                if (profile == null) {
                    setStyle("");
                } else {
                    var selectedProfile = profileService.getSelectedProfile();
                    boolean selected = selectedProfile != null && selectedProfile.equals(profile);

                    String color = (selected ? "#06c806" : "white");
                    setStyle(String.format("-fx-text-inner-color: black; -fx-background-color: %s;", color));
                }
            }
        });

        createProfileButton.setOnAction(event -> onCreate());
        exitProfileButton.setOnAction(event -> onSelect(null));
    }

    @Override
    protected void onSelect(Profile profile) {
        super.onSelect(profile);
        localProfilesListView.refresh();
    }

    @Override
    protected void onEdit(Profile profile, boolean isCreated, String title) {
        var changed = fxmlService.showInfoDialog(
                ProfileInfoController.class,
                profile,
                title, isCreated
        );

        if (changed) {
            profileService.save(profile);
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
}
