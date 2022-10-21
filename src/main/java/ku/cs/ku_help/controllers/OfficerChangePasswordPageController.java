package ku.cs.ku_help.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.ku_help.AlertBox;
import ku.cs.ku_help.models.Officer;
import ku.cs.ku_help.models.OfficerList;
import ku.cs.ku_help.service.DataSource;
import ku.cs.ku_help.service.OfficerListFileDataSource;

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

    private Officer officerLogin;

    private Officer officer;

    public void initialize() {
        dataSource = new OfficerListFileDataSource("data", "Officer.csv");
        officerList = dataSource.readData();
        officerLogin = (Officer) com.github.saacsos.FXRouter.getData();
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

        officer = officerList.findOfficer(officerLogin.getUsername());

        if (officer.isPassword(officerOldPasswordInput)) {
            if (officerNewPasswordInput.equals(officerConfirmNewPasswordInput)){
                clearErrorLabel();
                clearSuccessLabel();
                officerList.changePassword(officerOldUsernameInput, officerNewPasswordInput);
                dataSource.writeData(officerList);
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
        officerList = dataSource.readData();
    }
}
