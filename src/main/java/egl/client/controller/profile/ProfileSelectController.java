package egl.client.controller.profile;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.Controller;
import egl.client.model.core.profile.Profile;
import egl.client.service.FxmlService;
import egl.client.service.model.core.ProfileService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ProfileSelectController implements Controller {

    protected final FxmlService fxmlService;
    protected final ProfileService profileService;

    @FXML protected Button createProfileButton;
    @FXML protected Button exitProfileButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createProfileButton.setOnAction(event -> onCreate());
        exitProfileButton.setOnAction(event -> onSelect(null));
    }

    protected void onSelect(Profile profile) {
        profileService.select(profile);
    }

    protected void onCreate() {
        Profile profile = new Profile();
        onEdit(profile, true, "Новый профиль");
    }

    protected void onEdit(Profile profile) {
        onEdit(profile, false, "Изменить данные");
    }

    protected abstract void onEdit(Profile profile, boolean isCreated, String title);

    @Override
    public void prepareToClose() {

    }
}
