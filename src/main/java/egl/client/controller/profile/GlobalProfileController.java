package egl.client.controller.profile;

import egl.client.model.global.profile.GlobalCredentials;
import egl.client.model.global.profile.GlobalProfile;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.GlobalCredentialsService;
import egl.client.service.model.profile.GlobalProfileService;
import egl.client.view.text.LabeledTextField;
import egl.client.model.core.profile.Credentials;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class GlobalProfileController extends ProfileSelectController<GlobalProfile> {

    private final GlobalCredentialsService globalCredentialsService;

    @FXML
    private GridPane loginGridPane;
    @FXML
    private Text loginInfoText;
    @FXML
    private LabeledTextField loginTextField;
    @FXML
    private LabeledTextField passwordTextField;
    @FXML
    private Button editProfileButton;
    @FXML
    private Button loginButton;
    @FXML
    private Text errorText;

    public GlobalProfileController(
            FxmlService fxmlService,
            GlobalProfileService globalProfileService,
            GlobalCredentialsService globalCredentialsService
    ) {
        super(fxmlService, globalProfileService);
        this.globalCredentialsService = globalCredentialsService;
    }

    @Override
    protected GlobalProfile createProfile() {
        return new GlobalProfile();
    }

    @Override
    protected void onEdit(GlobalProfile globalProfile, boolean isCreated, String title) {
        var globalCredentials = (isCreated
                ? new GlobalCredentials(globalProfile)
                : globalCredentialsService.findBy(globalProfile)
        );

        // TODO check errors
        if (null == globalCredentials) {
            return;
        }

        var changed = fxmlService.showInfoDialog(
                CredentialsInfoController.class,
                globalCredentials,
                title, isCreated
        );

        if (changed) {
            globalCredentialsService.save(globalCredentials);
            onSelect(globalProfile);
        }
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        loginGridPane.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    protected void onSelect(GlobalProfile profile) {
        super.onSelect(profile);
        showSelectedProfile();
    }

    private void showSelectedProfile() {
        var selectedProfile = profileService.getSelectedProfile();

        boolean selected = (null != selectedProfile);
        String profileText = (selected ? selectedProfile.getName() : "не выбран");
        loginInfoText.setText("Глобальный профиль: " + profileText);

        editProfileButton.setVisible(selected);
    }

    @Override
    public void prepareToShow() {
        showSelectedProfile();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        editProfileButton.setOnAction(event -> onEdit(profileService.getSelectedProfile()));

        initLogin();
    }

    private void initLogin() {
        loginButton.setOnAction(event -> onLogin());
    }

    private void showLoginError() {
        errorText.setText("Некорректный логин/пароль");
    }

    private void onLogin() {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();

        Credentials credentials = globalCredentialsService.findBy(login);
        if (null == credentials) {
            showLoginError();
            return;
        }

        long loginPasswordHash = Credentials.calculatePasswordHash(password);
        if (credentials.getPasswordHash() != loginPasswordHash) {
            showLoginError();
            return;
        }

        loginTextField.setText("");
        passwordTextField.setText("");
        errorText.setText("");

        onSelect((GlobalProfile) credentials.getProfile());
    }
}
