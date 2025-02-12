package org.library.btl_oop16_library.controller.dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.library.btl_oop16_library.utils.general.ApplicationAlert;

public class AddBookDialogController {
    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField quantityField;

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
     private void onConfirmButtonClick(ActionEvent event) {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            return;
        }
        ApplicationAlert.addSuccess();
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    public int getQuantity() {
        if(quantityField.getText().isEmpty()) {
            return -1;
        }
        return Integer.parseInt(quantityField.getText());
    }
}
