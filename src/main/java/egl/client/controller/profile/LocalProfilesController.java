package egl.client.controller.profile;

import egl.client.controller.Controller;
import egl.client.model.profile.LocalProfile;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.LocalProfileService;
import egl.client.view.table.list.InfoSelectEditRemoveListView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class LocalProfilesController implements Controller {

    private final FxmlService fxmlService;
    private final LocalProfileService localProfileService;

    @FXML private InfoSelectEditRemoveListView<LocalProfile> localProfilesListView;
    @FXML private Button createProfileButton;
    @FXML private Button exitProfileButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localProfilesListView.setService(localProfileService);
        localProfilesListView.setOnSelect(this::onSelect);
        localProfilesListView.setOnEdit(this::onEdit);

        localProfilesListView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(LocalProfile profile, boolean empty) {
                super.updateItem(profile, empty);

                if (profile == null) {
                    setStyle("");
                } else {
                    var selectedProfile = localProfileService.getSelectedProfile();
                    boolean selected = selectedProfile != null && selectedProfile.equals(profile);

                    String color = (selected ? "06c806" : "white");
                    setStyle(String.format("-fx-text-inner-color: black; -fx-background-color: #%s;", color));
                }
            }
        });

        createProfileButton.setOnAction(event -> onCreate());
        exitProfileButton.setOnAction(event -> onSelect(null));
    }

    private void onSelect(LocalProfile localProfile) {
        localProfileService.select(localProfile);
        localProfilesListView.refresh();
    }

    private void onCreate() {
        LocalProfile localProfile = new LocalProfile();
        onEdit(localProfile, true, "Новый профиль");
    }

    private void onEdit(LocalProfile localProfile) {
        onEdit(localProfile, false, "Изменить данные");
    }

    private void onEdit(LocalProfile localProfile, boolean isCreated, String title) {
        var changed = fxmlService.showInfoDialog(
                LocalProfileInfoController.class,
                localProfile,
                title, isCreated
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
