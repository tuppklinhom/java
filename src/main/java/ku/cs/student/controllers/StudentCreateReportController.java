package ku.cs.student.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ku.cs.student.models.*;
import ku.cs.student.service.CategoryListFileDataSource;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.ReportListFileDataSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class StudentCreateReportController {
    private User user;
    @FXML
    private TextField headlineTextField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TextArea contentTextArea;

    private DataSource<CategoryList> categoryDataSource;

    private DataSource<ReportList> reportDataSource;

    private CategoryList categoryList;

    private ReportList reportList;

    public void initialize(){
        categoryDataSource = new CategoryListFileDataSource("data", "Category.csv");//category for select choice box
        categoryList = categoryDataSource.readData(); // read category
        reportDataSource = new ReportListFileDataSource("data", "Report.csv");// read report for add later
        reportList = reportDataSource.readData();
        user = (User) com.github.saacsos.FXRouter.getData();// get user who reported
        showChoiceBoxView();
    }

    private void showChoiceBoxView(){

        categoryChoiceBox.getItems().addAll(categoryList.getAllCategories());
        categoryChoiceBox.setOnAction(this::handleChoiceBoxButton);
    }


    public void handleChoiceBoxButton(ActionEvent actionEvent){
        String category = categoryChoiceBox.getValue();//decoy for select choice box (may not be used)
    }

    public void handleSubmitButton(ActionEvent actionEvent){
        String reporter = user.getName();
        String headline = headlineTextField.getText();
        String category = categoryChoiceBox.getValue();
        String content = contentTextArea.getText();
        //get every input
        Report newReport = new Report(reporter, headline, content, category);
        //create new report

        reportList.addReport(newReport);// add report
        reportDataSource.writeData(reportList);
        //write new data

        try {
            com.github.saacsos.FXRouter.goTo("student_main_page",user);
        } catch (IOException e) {
            System.err.println("ไปทีหน้า student_create_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
        //go back to main page


    }


    public void handleBackButton(){
        try {
            com.github.saacsos.FXRouter.goTo("student_main_page",user);
        } catch (IOException e) {
            System.err.println("ไปทีหน้า student_create_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }

    }

}
