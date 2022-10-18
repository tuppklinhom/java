package ku.cs.student.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.student.AlertBox;
import ku.cs.student.models.Officer;
import ku.cs.student.models.OfficerList;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.OfficerListFileDataSource;

import java.io.IOException;

public class OfficerChangePasswordPageController {
    @FXML
    private TextField userNameOfficerResetTextField;

    @FXML
    private TextField oldPasswordResetOfficerPasswordField;

    @FXML
    private TextField newPassOfficerPasswordField;

    @FXML
    private TextField conFirmNewPassOfficerPasswordField;

    @FXML
    private Label successLabel;

    @FXML
    private Label errorLabel;

    private DataSource<OfficerList> dataSource;

    private OfficerList officerList;

    private Officer officer;

    public void initialize() {
        dataSource = new OfficerListFileDataSource("data", "Officer.csv");
        officerList = dataSource.readData();
        officer = (Officer) com.github.saacsos.FXRouter.getData();
        clearErrorLabel();
        clearSuccessLabel();
    }

    private void clearErrorLabel() {
        errorLabel.setText("");
    }

    private void clearSuccessLabel() {
        successLabel.setText("");
    }

    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("officer_main_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า officer_main_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }

    public void handleResetPassword() {
        String officerOldUsernameInput = userNameOfficerResetTextField.getText();
        String officerOldPasswordInput = oldPasswordResetOfficerPasswordField.getText();
        String officerNewPasswordInput = newPassOfficerPasswordField.getText();
        String officerConfirmNewPasswordInput = conFirmNewPassOfficerPasswordField.getText();

        if (officerNewPasswordInput.equals(officerConfirmNewPasswordInput)) {

            Officer officer = officerList.findOfficer(officerOldUsernameInput);

            if (officer == null) {
                errorLabel.setText("Username or Password Incorrect.");
                AlertBox.display("Alert", "Username or Password Incorrect.");
            } else {
                if (officer.isPassword(officerOldPasswordInput)) {
                    officer.changePassword(officerNewPasswordInput);
                    officerList.addOfficer(officer);
                    dataSource.writeData(officerList);
                    errorLabel.setText("");
                    successLabel.setText("Successfully change password");
                }
            }
        } else {
            errorLabel.setText("New Password doesn't match.");
        }
    }

}
