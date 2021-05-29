package egl.client.controller.profile;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.ProfileService;
import egl.core.model.profile.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ProfileSelectController<ProfileType extends Profile> implements Controller {

    protected final FxmlService fxmlService;
    protected final ProfileService<ProfileType> profileService;

    @FXML protected Button createProfileButton;
    @FXML protected Button exitProfileButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createProfileButton.setOnAction(event -> onCreate());
        exitProfileButton.setOnAction(event -> onSelect(null));
    }

    protected void onSelect(ProfileType profile) {
        profileService.select(profile);
    }

    protected abstract ProfileType createProfile();

    protected void onCreate() {
        ProfileType profile = createProfile();
        onEdit(profile, true, "Новый профиль");
    }

    protected void onEdit(ProfileType profile) {
        onEdit(profile, false, "Изменить данные");
    }

    protected abstract void onEdit(ProfileType profile, boolean isCreated, String title);

    @Override
    public void prepareToClose() {

    }
}
