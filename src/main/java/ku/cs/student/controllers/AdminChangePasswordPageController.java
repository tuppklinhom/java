package ku.cs.student.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.student.AlertBox;
import ku.cs.student.models.Admin;
import ku.cs.student.models.AdminList;
import ku.cs.student.service.AdminListFileDataSource;
import ku.cs.student.service.DataSource;

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
    private String adminUsernameInput;
    private String adminPasswordInput;
    private String adminNewPassInput;
    private String adminNewConPassInput;

    public void initialize() {
        dataSource = new AdminListFileDataSource("data", "admin.csv");
        adminList = dataSource.readData();
        clearErrorLabel();
        clearSuccessLabel();
    }

    private void clearErrorLabel() {
        errorLabel.setText("");
    }

    private void clearSuccessLabel() {
        successLabel.setText("");
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

        if (adminNewPassInput.equals(adminNewConPassInput)) {

            Admin admin = adminList.findByUsername(adminUsernameInput);

            if (admin == null) {
                errorLabel.setText("Username or Password Incorrect.");
                AlertBox.display("Alert", "Username or Password Incorrect.");
            } else {
                if (admin.isPassword(adminPasswordInput)) {
                    admin.changePassword(adminNewPassInput);
                    dataSource.writeData(adminList);
                    errorLabel.setText("");
                    successLabel.setText("Successfully change password");
                }
            }
        } else {
            errorLabel.setText("New Password doesn't match.");
        }
    }
}
