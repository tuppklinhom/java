package ku.cs.student.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.github.saacsos.FXRouter;
import ku.cs.student.models.OfficerList;
import ku.cs.student.service.OfficerListHardCodeDataSource;

import java.io.IOException;

public class OfficerLoginPageController {
    @FXML
    private TextField officerUsernameTextField;
    @FXML
    private  TextField officerPasswordTextField;
    @FXML
    private Label officerErrorLabel;


    private OfficerListHardCodeDataSource dataSource;


    private OfficerList officerList;
    // ตัวเเปร
    private String officerUsernameInput;
    private String officerPasswordInput;

    public void initialize(){
        dataSource = new OfficerListHardCodeDataSource();
        officerList = dataSource.getOfficerList();
        clearErrorLabel();
    }

    private void clearErrorLabel(){ officerErrorLabel.setText("");}

    public void handleLoginButton(){
        officerUsernameInput = officerUsernameTextField.getText();
        officerPasswordInput = officerPasswordTextField.getText();

        for (int i = 0; i < officerList.size(); i++) {
            if (officerList.indexOf(i).getUsername().equals(officerUsernameInput)){
                if (officerList.indexOf(i).getPassword().equals(officerPasswordInput)) {
                    //************************  OFFICER MAIN PAGE REQUIRE
                    try {
                        FXRouter.goTo("officer_main_page");
                        //********************  OFFICER MAIN PAGE REQUIRE
                    } catch (IOException e) {
                        System.err.println("ไปทีหน้า officer main page ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกําหนด route");
                        }
                }
            }
        }
        officerErrorLabel.setText("Username or Password incorrect.");
        officerUsernameTextField.clear();
        officerPasswordTextField.clear();
    }

    public void handleBackButton(){
        try {
            FXRouter.goTo("student_login_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า login page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }



}
