package egl.client.controller.profile.global;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.controller.profile.CredentialsInfoController;
import egl.client.controller.profile.ProfileSelectController;
import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.global.GlobalCredentialsService;
import egl.client.service.model.global.GlobalProfileService;
import egl.client.view.text.LabeledTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class GlobalProfileSelectController extends ProfileSelectController {

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

    public GlobalProfileSelectController(FxmlService fxmlService,
                                         GlobalProfileService profileService,
                                         GlobalCredentialsService globalCredentialsService) {
        super(fxmlService, profileService);
        this.globalCredentialsService = globalCredentialsService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        editProfileButton.setOnAction(event ->
                profileService.getSelectedProfile().ifPresent(this::onEdit)
        );

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

        try {
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
        } catch (EntityServiceException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Проблема при проверке логина/пароля",
                    ButtonType.OK).show();
        }
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
        var selectedProfileName = profileService.getSelectedProfile().map(Profile::getName);

        String profileText = selectedProfileName.orElse("не выбран");
        loginInfoText.setText("Глобальный профиль: " + profileText);

        editProfileButton.setVisible(selectedProfileName.isPresent());
    }

    @Override
    public void prepareToShow() {
        showSelectedProfile();
    }
}
