package ku.cs.ku_help.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.ku_help.AlertBox;
import ku.cs.ku_help.models.Admin;
import ku.cs.ku_help.models.AdminList;
import ku.cs.ku_help.service.AdminListFileDataSource;
import ku.cs.ku_help.service.DataSource;

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
    @FXML
    private Label successLabel;
//    @FXML
//    void showAlert(ActionEvent event){
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Oh mai na");
//        alert.setContentText("TTTTT");
//        Optional<ButtonType> result = alert.showAndWait();
//    }

    // เป็น ALERT อีกเเบบที่ไม่รู้ว่าจะ custom มันยังไงถ้าอยากรู้ว่าเป็นยังไงก็ลองเอาคอมเเม้นของ เเล้วลองใช้ดูนะ (เลื่อนไปเอาด้านล่างออกด้วยนะ)
    private DataSource<AdminList> dataSource;
    private AdminList adminList;

    private Admin admin;
    private String adminUsernameInput;
    private String adminPasswordInput;
    private String adminNewPassInput;
    private String adminNewConPassInput;

    public void initialize() {
        dataSource = new AdminListFileDataSource("data", "admin.csv");
        adminList = dataSource.readData();
        admin = (Admin) com.github.saacsos.FXRouter.getData();
        clearErrorLabel();
        clearSuccessLabel();
    }

    private void clearErrorLabel() {
        errorLabel.setText("");
    }

    private void clearSuccessLabel() {
        successLabel.setText("");
    }
    private void clearTextField(){
        userNameAdminResetTextField.clear();
        oldPasswordResetAdminPasswordField.clear();
        newPassAdminPasswordField.clear();
        conNewPassAdminPasswordField.clear();
    }

    public void handleBackButton() {
        try {
            com.github.saacsos.FXRouter.goTo("admin_main_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า Admin Page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }

    public void handleResetPasswordAdmin() {
        adminUsernameInput = userNameAdminResetTextField.getText();
        adminPasswordInput = oldPasswordResetAdminPasswordField.getText();
        adminNewPassInput = newPassAdminPasswordField.getText();
        adminNewConPassInput = conNewPassAdminPasswordField.getText();

        if (admin.isPassword(adminPasswordInput) || admin.isUsername(adminUsernameInput)) {
            if (adminNewPassInput.equals(adminNewConPassInput)){
                clearErrorLabel();
                clearSuccessLabel();
                adminList.changePassword(adminUsernameInput, adminNewPassInput);
                dataSource.writeData(adminList);
                successLabel.setText("Successfully changed password.");
            }
            else{
                clearErrorLabel();
                clearSuccessLabel();
                errorLabel.setText("New Password does not match");
            }
        }
        else {
            clearErrorLabel();
            clearSuccessLabel();
            errorLabel.setText("Username or Password Incorrect.");
        }
    }
}
