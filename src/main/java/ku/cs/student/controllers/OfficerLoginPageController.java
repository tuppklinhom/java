package ku.cs.student.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.github.saacsos.FXRouter;
import ku.cs.student.models.Officer;
import ku.cs.student.models.OfficerList;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.OfficerListFileDataSource;

import java.io.IOException;

public class OfficerLoginPageController {
    @FXML
    private TextField officerUsernameTextField;
    @FXML
    private  TextField officerPasswordTextField;
    @FXML
    private Label officerErrorLabel;


    private DataSource<OfficerList> dataSource;


    private OfficerList officerList;
    // ตัวเเปร
    private String officerUsernameInput;
    private String officerPasswordInput;

    public void initialize(){
        dataSource = new OfficerListFileDataSource("data", "Officer.csv");
        officerList = dataSource.readData();
        clearErrorLabel();
    }

    private void clearErrorLabel(){ officerErrorLabel.setText("");}

    public void handleLoginButton(){
        officerUsernameInput = officerUsernameTextField.getText();
        officerPasswordInput = officerPasswordTextField.getText();

        Officer officer = officerList.findOfficer(officerUsernameInput);

        if (officer != null){
            if(officer.isPassword(officerPasswordInput)){
                officerList.findOfficer(officer.getUsername()).setLatestLoginDate();
                dataSource.writeData(officerList);
                try {
                    FXRouter.goTo("officer_main_page", officer);
                } catch (IOException e) {
                    System.err.println("ไปทีหน้า officer main page ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกําหนด route");
                }
            }
        }
        officerErrorLabel.setText("Username or Password incorrect.");
        officerUsernameTextField.clear();
        officerPasswordTextField.clear();
    }

    public void handleBackButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("student_login_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า login page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }

    public void handleChangePasswordPageButton() {
        try {
            FXRouter.goTo("officer_change_password_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า officer change password page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }


}
