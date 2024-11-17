package org.library.btl_oop16_library.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.library.btl_oop16_library.Model.User;
import org.library.btl_oop16_library.Util.ApplicationAlert;
import org.library.btl_oop16_library.Util.UserDBConnector;

public class AddUserDialogController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConfirmButtonClick(ActionEvent event) {

        String name = nameField.getText();
        String userName = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();

        String gmailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        if (!email.matches(gmailRegex)) {
            ApplicationAlert.wrongEmailPattern();
            return;
        }
        UserDBConnector userDBConnector = UserDBConnector.getInstance();
        userDBConnector.addToDB(new User(name, email, phoneNumber, address, userName, password));

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
