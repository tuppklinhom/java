package ku.cs.student.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdminChangePasswordPageController {
    @FXML
    private TextField userNameAdminResetTextField;
    @FXML
    private PasswordField oldPasswordResetAdminPasswordField;
    @FXML
    private PasswordField newPassAdminPasswordField;
    @FXML
    private PasswordField conNewPassAdminPasswordField;
    @FXML
    private Label errorLabel;

    private String adminUsernameInput;
    private String adminPasswordInput;

    public void initialize(){
        clearErrorLabel();
    }

    private void clearErrorLabel(){
        errorLabel.setText("");
    }

    public void handleBackButton(){
        try {
            com.github.saacsos.FXRouter.goTo("admin_main_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า Admin Page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }

    public void handleResetPasswordAdmin(){
        adminUsernameInput = userNameAdminResetTextField.getText();
        adminPasswordInput = oldPasswordResetAdminPasswordField.getText();
    }
}
