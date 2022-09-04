package ku.cs.student.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.student.models.StudentList;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.StudentListFileDataSource;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class StudentLoginPageController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorLabel;

    private String usernameInput;
    private String passwordInput;
//    private StudentListHardCodeDataSource dataSource;
    private DataSource<StudentList> dataSource;

    private StudentList studentList;


    public void initialize(){
//        dataSource = new StudentListHardCodeDataSource();
        dataSource = new StudentListFileDataSource("data","Student.csv");
//        studentList = dataSource.getStudentList()
        studentList = dataSource.readData();
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
            FXRouter.goTo("officer_login_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า officer page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }


    //************** Don't have ADMIN LOGIN PAGE YET, so gonna use officer_page instead right now.
    public void handleGotoAdminPage(){
        try {
            FXRouter.goTo("admin_login_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า admin page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }
    //************** Don't have ADMIN LOGIN PAGE YET.







}
