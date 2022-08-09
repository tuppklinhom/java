package ku.cs.student.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.student.models.Student;
import ku.cs.student.models.StudentList;
import ku.cs.student.service.StudentListHardCodeDataSource;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class LoginPageController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorLabel;

    private StudentListHardCodeDataSource dataSource;

    private StudentList studentList;

    // **************************
    //ทำตัวเช็คล็อคอิน
    private String usernameInput;
    private String passwordInput;
    //***************************

    public void initialize(){
        dataSource = new StudentListHardCodeDataSource();
        studentList = dataSource.getStudentList();
        clearErrorLabel();

    }

    private void clearErrorLabel(){
        errorLabel.setText("");
    }

    public void handleLoginButton() {
        usernameInput = usernameTextField.getText();
        passwordInput = passwordTextField.getText();

        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.indexOf(i).getUsername().equals(usernameInput)) {
                if (studentList.indexOf(i).getPassword().equals(passwordInput)) {
                    try {
                        FXRouter.goTo("student_main_page");
                    } catch (IOException e) {
                        System.err.println("ไปทีหน้า student main page ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกําหนด route");
                        }
                }
            }
        }
        errorLabel.setText("Username or Password incorrect.");
        usernameTextField.clear();
        passwordTextField.clear();

    }

    public void handleGotoOfficerPage(){
        try {
            FXRouter.goTo("officer_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า officer page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }


    //************** Don't have ADMIN LOGIN PAGE YET, so gonna use officer_page instead right now.
    public void handleGotoAdminPage(){
        try {
            FXRouter.goTo("officer_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า officer page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }
    //************** Don't have ADMIN LOGIN PAGE YET.







}
