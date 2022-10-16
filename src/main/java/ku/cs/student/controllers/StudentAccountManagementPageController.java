package ku.cs.student.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.student.models.StudentList;
import ku.cs.student.models.User;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.StudentListFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class StudentAccountManagementPageController {
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private  PasswordField confirmNewPasswordField;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Label errorLabel;


    private User user;
    private DataSource<StudentList> dataSource;
    private StudentList studentList;

    public void initialize() {
        dataSource = new StudentListFileDataSource("data", "Student.csv");
        studentList = dataSource.readData();
        user = (User) com.github.saacsos.FXRouter.getData();
        clearErrorLabel();
        showProfileImageView();
    }

    private void clearErrorLabel() {
        errorLabel.setText("");

    }

    private void showProfileImageView(){
        File imageFile = new File(user.getImagePath());
        profileImageView.setImage(new Image(imageFile.toURI().toString()));
    }

    public void handleChangePasswordButton(ActionEvent actionEvent){
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmNewPasswordField.getText();

        if (newPassword.equals(confirmPassword)){
            user.changePassword(newPassword);
            studentList.addStudent(user);
            dataSource.writeData(studentList);
            errorLabel.setText("Successfully changed password.");
        }
        else{
            errorLabel.setText("Password do not match");
        }

    }

    public void handleChangeProfileButton(ActionEvent event){
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
                user.setImagePath(destDir + "/" + filename);
                showProfileImageView();
                studentList.addStudent(user);
                dataSource.writeData(studentList);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("student_main_page", user);
        } catch (IOException e) {
            System.err.println("ไปทีหน้า student_create_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }


}
