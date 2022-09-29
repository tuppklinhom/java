package ku.cs.student.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.student.models.AdminList;
import ku.cs.student.service.AdminListFileDataSource;

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

        for (int i =0; i < adminList.size(); i++) {
            if (adminList.indexOf(i).getUsername().equals(adminUsernameInput)){
                if (adminList.indexOf(i).getPassword().equals(adminPasswordInput)) {
                    try {
                        com.github.saacsos.FXRouter.goTo("admin_main_page");
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
