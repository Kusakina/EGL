package egl.client.controller.profile.info;

import java.net.URL;
import java.util.ResourceBundle;

import egl.client.service.FxmlService;
import egl.client.view.info.EntityInfoView;
import egl.client.view.text.LabeledTextField;
import egl.core.model.profile.Credentials;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class CredentialsLoginPasswordView extends GridPane
        implements EntityInfoView<Credentials>, Initializable {

    @FXML private LabeledTextField loginTextField;
    @FXML private Text requiredActionsText;
    @FXML private LabeledTextField currentPasswordTextField;
    @FXML private LabeledTextField newPasswordTextField;
    @FXML private LabeledTextField newPasswordAgainTextField;

    public CredentialsLoginPasswordView() {
        FxmlService.loadView(this, CredentialsLoginPasswordView.class, true);
    }

    private String startLogin;
    private long expectedPasswordHash;

    @Override
    public void initData(Credentials credentials, boolean isCreated) {
        this.startLogin = (null == credentials.getLogin() ? "" : credentials.getLogin());
        loginTextField.setText(startLogin);

        if (isCreated) {
            requiredActionsText.setVisible(false);
            currentPasswordTextField.setVisible(false);
        } else {
            currentPasswordTextField.setText("");
            this.expectedPasswordHash = credentials.getPasswordHash();
        }

        newPasswordTextField.setText("");
        newPasswordAgainTextField.setText("");
    }

    @Override
    public void validateData() {
        String login = loginTextField.getText();
        if (login.isBlank()) {
            throw new IllegalArgumentException("Логин не может быть пустым");
        }

        String newPassword = newPasswordTextField.getText();
        if (!newPasswordAgainTextField.getText().equals(newPassword)) {
            throw new IllegalArgumentException("Введенные новые пароли не совпадают");
        }

        boolean isCreated = !currentPasswordTextField.isVisible();

        long actualPasswordHash = Credentials.calculatePasswordHash(currentPasswordTextField.getText());
        boolean infoCanBeChanged = isCreated || (expectedPasswordHash == actualPasswordHash);

        boolean infoWasChanged = !startLogin.equals(login);
        infoWasChanged |= !newPassword.isBlank();

        if (infoWasChanged && !infoCanBeChanged) {
            throw new IllegalArgumentException("Введен неверный текущий пароль");
        }
    }

    @Override
    public void fillData(Credentials credentials) {
        credentials.setLogin(loginTextField.getText());

        String newPassword = newPasswordTextField.getText();
        if (!newPassword.isBlank()) {
            long newPasswordHash = Credentials.calculatePasswordHash(newPassword);
            credentials.setPasswordHash(newPasswordHash);
        }
    }

    @Override
    public void setPrefSize(double prefWidth, double prefHeight) {
        super.setPrefSize(prefWidth, prefHeight);

        for (Node node : getChildrenUnmodifiable()) {
            if (!(node instanceof LabeledTextField)) continue;

            LabeledTextField textField = (LabeledTextField) node;

            RowConstraints rowConstraints = getRowConstraints().get(getRowIndex(node));
            ColumnConstraints columnConstraints = getColumnConstraints().get(getColumnIndex(node));

            textField.setPrefSize(
                    prefWidth * columnConstraints.getPercentWidth() / 100,
                    prefHeight * rowConstraints.getPercentHeight() / 100
            );
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeRows();
        initializeColumns();
    }

    private void initializeRows() {
        setRowIndex(loginTextField, 0);
        setRowIndex(newPasswordTextField, 0);

        setRowIndex(requiredActionsText, 1);

        setRowIndex(currentPasswordTextField, 2);
        setRowIndex(newPasswordAgainTextField, 2);
    }

    private void initializeColumns() {
        setColumnIndex(loginTextField, 0);
        setColumnIndex(requiredActionsText, 0);
        setColumnIndex(currentPasswordTextField, 0);

        setColumnIndex(newPasswordTextField, 1);
        setColumnIndex(newPasswordAgainTextField, 1);
    }
}
