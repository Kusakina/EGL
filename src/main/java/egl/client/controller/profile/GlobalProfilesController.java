package egl.client.controller.profile;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.service.FxmlService;
import egl.client.service.model.profile.GlobalCredentialsService;
import egl.client.service.model.profile.GlobalProfileService;
import egl.client.view.text.LabeledTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class GlobalProfilesController extends ProfileSelectController {

    private final GlobalCredentialsService globalCredentialsService;

    @FXML
    private TabPane activitiesTabPane;

    @FXML
    private Tab globalProfileTab;

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

    public GlobalProfilesController(
            FxmlService fxmlService,
            GlobalProfileService globalProfileService,
            GlobalCredentialsService globalCredentialsService
    ) {
        super(fxmlService, globalProfileService);
        this.globalCredentialsService = globalCredentialsService;
    }

    @Override
    protected void onEdit(Profile profile, boolean isCreated, String title) {
        var credentials = (isCreated
                ? new Credentials(profile)
                : globalCredentialsService.findBy(profile)
        );

        // TODO check errors
        if (null == credentials) {
            return;
        }

        var changed = fxmlService.showInfoDialog(
                CredentialsInfoController.class,
                credentials,
                title, isCreated
        );

        if (changed) {
            globalCredentialsService.save(credentials);
            onSelect(profile);
        }
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        loginGridPane.setPrefSize(parentWidth, parentHeight);
    }

    @Override
    protected void onSelect(Profile profile) {
        super.onSelect(profile);
        showSelectedProfile();
    }

    private void showSelectedProfile() {
        activitiesTabPane.getSelectionModel().select(globalProfileTab);

        var selectedProfile = profileService.getSelectedProfile();

        boolean selected = Optional.ofNullable(selectedProfile).isPresent();
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

        onSelect(credentials.getProfile());
    }
}
