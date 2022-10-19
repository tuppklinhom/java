package ku.cs.ku_help.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.ku_help.models.Admin;
import ku.cs.ku_help.models.AdminList;
import ku.cs.ku_help.service.AdminListFileDataSource;

import java.io.IOException;

public class AdminLoginPageController {
    @FXML
    private TextField usernameAdminLoginTextField;
    @FXML
    private PasswordField passwordAdminLoginPasswordField;
    @FXML
    private Label errorLabel;

    private AdminListFileDataSource dataSource;

    private AdminList adminList;

    private String adminUsernameInput;
    private String adminPasswordInput;

    private void clearErrorLabel(){
        errorLabel.setText("");
    }

    public void handleLoginAdminButton(){
        adminUsernameInput = usernameAdminLoginTextField.getText();
        adminPasswordInput = passwordAdminLoginPasswordField.getText();

        Admin adminUser = adminList.findByUsername(adminUsernameInput);

        if(adminUser != null) {
            if(adminUser.isPassword(adminPasswordInput)){
                try {
                    com.github.saacsos.FXRouter.goTo("admin_main_page", adminUser);
                } catch (IOException e) {
                    System.err.println("ไปทีหน้า admin main page ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกําหนด route");
                }
            }

        }
            errorLabel.setText("Username or Password incorrect.");
            usernameAdminLoginTextField.clear();
            passwordAdminLoginPasswordField.clear();
    }
    public void handleBackButton(){
        try {
            com.github.saacsos.FXRouter.goTo("student_login_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า login page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }

    public void initialize(){
        dataSource = new AdminListFileDataSource("data", "admin.csv");
        adminList = dataSource.readData();
        clearErrorLabel();
    }
}



