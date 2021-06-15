package egl.client.controller.profile.global;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import egl.client.controller.profile.CredentialsInfoController;
import egl.client.controller.profile.ProfileSelectController;
import egl.client.model.core.profile.Credentials;
import egl.client.model.core.profile.Profile;
import egl.client.service.ErrorService;
import egl.client.service.FxmlService;
import egl.client.service.model.EntityServiceException;
import egl.client.service.model.global.GlobalCredentialsService;
import egl.client.service.model.global.GlobalProfileService;
import egl.client.view.text.LabeledTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class GlobalProfileSelectController extends ProfileSelectController {

    private static final String INCORRECT_CREDENTIALS_ERROR_MESSAGE =
            "Некорректный логин/пароль";

    private static final String DATABASE_CONNECTION_ERROR_MESSAGE =
            "Проблема при подключении к глобальной базе данных";

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

    private void showErrorMessage(String errorMessage) {
        errorText.setText(errorMessage);
    }

    private void onLogin() {
        String login = loginTextField.getText();

        try {
            globalCredentialsService.findBy(login)
                    .ifPresentOrElse(
                            this::tryAuthorizeWith,
                            () -> showErrorMessage(INCORRECT_CREDENTIALS_ERROR_MESSAGE)
                    );
        } catch (EntityServiceException e) {
            showErrorMessage(DATABASE_CONNECTION_ERROR_MESSAGE);
        }
    }

    private void tryAuthorizeWith(Credentials credentials) {
        String password = passwordTextField.getText();

        long loginPasswordHash = Credentials.calculatePasswordHash(password);
        if (credentials.getPasswordHash() != loginPasswordHash) {
            showErrorMessage(INCORRECT_CREDENTIALS_ERROR_MESSAGE);
        }

        loginTextField.setText("");
        passwordTextField.setText("");
        errorText.setText("");

        onSelect(credentials.getProfile());
    }

    @Override
    protected void onEdit(Profile profile, boolean isCreated, String title) {
        try {
            var credentialsOptional = (isCreated
                    ? Optional.of(new Credentials(profile))
                    : globalCredentialsService.findBy(profile)
            );

            credentialsOptional.ifPresent(credentials -> {
                var changed = fxmlService.showInfoDialog(
                        CredentialsInfoController.class,
                        credentials,
                        title, isCreated
                );

                if (changed) {
                    try {
                        globalCredentialsService.save(credentials);
                    } catch (EntityServiceException e) {
                        ErrorService.showErrorAlert(
                                "Профиль не был сохранен из-за проблем " +
                                "с подключением к глобальной базе данных"
                        );
                    }

                    onSelect(profile);
                }
            });
        } catch (EntityServiceException e) {
            showErrorMessage(DATABASE_CONNECTION_ERROR_MESSAGE);
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
        exitProfileButton.setVisible(selectedProfileName.isPresent());
    }

    @Override
    public void prepareToShow() {
        showSelectedProfile();
    }
}
