package ku.cs.student.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.student.models.Student;
import ku.cs.student.models.StudentList;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.StudentListFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentRegistrationPageController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private TextField nameTextField;
    @FXML
    private ImageView profileImageView;


    private String usernameInput;
    private String passwordInput;
    private String confirmPasswordInput;

    private String imagePath;

    private String nameInput;



    private DataSource<StudentList> dataSource;

    private StudentList studentList;



    public void initialize(){
        dataSource = new StudentListFileDataSource("data","Student.csv");
        studentList = dataSource.readData();
        imagePath = "images/default.jpg";
        showImageProfile();
        clearErrorLabel();
    }

    private void showImageProfile(){
        File imageFile = new File(imagePath);
        profileImageView.setImage(new Image(imageFile.toURI().toString()));

    }
    private void clearErrorLabel() {
        errorLabel.setText("");
    }

    public void handleUploadPictureButton(ActionEvent event){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null){
            try{
                File destDir = new File("images");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                String[] fileSplit = file.getName().split("\\.");

                String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss")) + "_" + System.currentTimeMillis() + "." + fileSplit[fileSplit.length-1];
                Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath()+System.getProperty("file.separator")+filename);
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                profileImageView.setImage(new Image(target.toUri().toString()));
                imagePath = destDir + "/" + filename;
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

    public void handleCreateAccountButton(ActionEvent actionEvent){
        nameInput = nameTextField.getText();
        usernameInput = usernameTextField.getText();
        passwordInput = passwordField.getText();
        confirmPasswordInput = confirmPasswordField.getText();

        Student S = studentList.findStudent(usernameInput);

        if(S == null){
            if(passwordInput.equals(confirmPasswordInput)){
                Student newStudent = new Student(nameInput, usernameInput, passwordInput, imagePath);
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

    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("student_login_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า student_login_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }
}
