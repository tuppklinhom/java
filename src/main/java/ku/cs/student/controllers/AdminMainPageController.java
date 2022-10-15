package ku.cs.student.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import ku.cs.student.models.*;
import ku.cs.student.service.AdminListFileDataSource;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.OfficerListFileDataSource;
import ku.cs.student.service.StudentListFileDataSource;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

public class AdminMainPageController {

    @FXML
    private ListView allUserListView;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label adminUsernameLabel;
    @FXML
    private ImageView allUserImageView;
    @FXML
    private ImageView adminImageView;

    private OfficerList officerList;
    private StudentList studentList;
    private UserList userList;
    private OfficerListFileDataSource officerListDataSource;
    private StudentListFileDataSource studentListFileDataSource;
    private DataSource<AdminList> adminListDataSource;
    private AdminList adminUser;


    public void handleChangePassword(){
        try {
            com.github.saacsos.FXRouter.goTo("admin_change_password");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า login page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }

    public void handleBackButton(){
        try {
            com.github.saacsos.FXRouter.goTo("student_login_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า login page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }

    private void showListView(){
        allUserListView.getItems().clear();
        allUserListView.getItems().addAll(userList.getUsers());
        allUserListView.refresh();

    }


    private void cleaUsernameLabel(){
        usernameLabel.setText("");
    }
    private void clearCategoryLabel(){
        categoryLabel.setText("");
    }


    public void initialize(){
        officerListDataSource = new OfficerListFileDataSource("data", "Officer.csv");
        officerList = officerListDataSource.readData();

        studentListFileDataSource = new StudentListFileDataSource("data", "Student.csv");
        studentList = studentListFileDataSource.readData();
        adminListDataSource = new AdminListFileDataSource("data", "admin.csv");



        userList = new UserList();
        userList.addAllUser(studentList);
        userList.addAllUser(officerList);
        Collections.sort(userList.getUsers(), new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.compareTime(o1);
            }
        });
        clearCategoryLabel();
        cleaUsernameLabel();
        showListView();

    }



}
