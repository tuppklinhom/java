package ku.cs.student.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.student.models.Student;
import ku.cs.student.models.StudentList;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.StudentListFileDataSource;

public class StudentRegistrationPageController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField confirmPasswordTextField;

    private String usernameInput;
    private String passwordInput;
    private String confirmPasswordInput;

    private DataSource<StudentList> dataSource;

    private StudentList studentList;



    public void initialize(){
        dataSource = new StudentListFileDataSource("data","Student.csv");
        studentList = dataSource.readData();
        clearErrorLabel();
    }
    private void clearErrorLabel() {
        errorLabel.setText("");
    }

    public void handleCreateAccountButton(ActionEvent actionEvent){
        usernameInput = usernameTextField.getText();
        passwordInput = passwordTextField.getText();
        confirmPasswordInput = confirmPasswordTextField.getText();

        if(studentList.findStudent(usernameInput) == null){
            if(passwordInput.equals(confirmPasswordInput)){
                Student newStudent = new Student("NAME", usernameInput, passwordInput);
                studentList.addStudent(newStudent);
                dataSource.writeData(studentList);
                errorLabel.setText("Successfully create account");
            }
            else{
                    errorLabel.setText("Password do not match.");
            }
        }
        else{
            errorLabel.setText("This username is already in use.");
        }

    }
}
