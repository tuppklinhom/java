package ku.cs.student.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.student.models.*;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.OfficerListFileDataSource;
import ku.cs.student.service.ReportListFileDataSource;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class OfficerMainPageController {

    @FXML
    private ListView<Report> reportListView;

    @FXML
    private Label statusLabel;

    @FXML
    private Label solutionLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private Label headLineLabel;

    @FXML
    private Button selectedCategoryButton;

    private DataSource<ReportList> dataSource;
    private ReportList reportList;

    private Report tempReportForVote; //สำหรับการโหวต

    private Officer officer;

    private DataSource<OfficerList> dataSourceOfficerList;

    private OfficerList officerList;

    @FXML
    private TextArea officerTextArea;

    public void initialize(){
        dataSource = new ReportListFileDataSource("data","Report.csv");
        officer = (Officer) com.github.saacsos.FXRouter.getData(); // ข้อมูลของเจ้าหน้าที่ที่ล็อกอินเข้ามา โดนส่งผ่าน FXRouter
        reportList = dataSource.readData();

        reportList = reportList.filterBy(new Filterer<Report>() { // กรองเรื่องร้องเรียนจากหมวดหมู่ที่เจ้าหน้าที่รับผิดชอบเท่านั้น
            @Override
            public boolean filter(Report report) {
                return report.isCategory(officer.getCategory());
            }
        });

        dataSourceOfficerList = new OfficerListFileDataSource("data","Officer.csv");
        officerList = dataSourceOfficerList.readData();

        showListView();
        showCategoryChoiceBox();
        clearSelectReport();
        handleSelectedListView();
    }

    private void showListView(){
        reportListView.getItems().addAll(reportList.getAllReport());
        reportListView.refresh();
    }

    /*
    method ที่จะแสดงข้อมูลที่เราสามารถเลือกได้จาก ChoiceBox ( ข้อมูลที่เลือกได้มาจาก category ของ Class Report )
     */
    private void showCategoryChoiceBox() {
        Set<String> categoryListForChoiceBox = new TreeSet<String>();
        for (Report report : reportList.getAllReport()) {
            categoryListForChoiceBox.add(report.getCategory().trim());
        }
        categoryListForChoiceBox.add("หมวดหมู่ทั้งหมด");
        categoryChoiceBox.getItems().addAll(categoryListForChoiceBox);

        // *มาลบ comment นี้ทีหลัง : โค้ดนี้อาจใช้หรือไม่ใช้ก็ได้ อาจเอามาใช้ในอนาคต
//        categoryChoiceBox.setValue(categoryListForChoiceBox.get(categoryListForChoiceBox.size()-1));
//        categoryChoiceBox.setOnAction(this::handleSelectedCategory);
    }

    private void handleSelectedListView() {
        reportListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Report>() {
                    @Override
                    public void changed(ObservableValue<? extends Report> observableValue, Report oldValue, Report newValue) {
                        System.out.println(newValue + " is selected");
                        showSelectedReport(newValue);
                        showOfficerSortCategory();
                    }
                });
    }

    private void showSelectedReport(Report report){
        headLineLabel.setText(report.getHeadline());
        solutionLabel.setText(report.getContent());
        statusLabel.setText(report.getStatus());
        categoryLabel.setText(report.getCategory());
    }

    public void clearSelectReport(){
        headLineLabel.setText("");
        solutionLabel.setText("");
        statusLabel.setText("");
        categoryLabel.setText("");
    }


    public void handlePendingButton(){
        reportList = dataSource.readData();
        String headline = headLineLabel.getText(); // หา headline จาก String
        Report report = reportList.find(headline); // หา report ที่มี headline ตรงกันกับ headline ของ report
        report.updateStatus("ดำเนินการ");
//        int index = reportList.getAllReport().indexOf(report); // หา index ของ report
//        reportList.getAllReport().get(index).updateStatus("ดำเนินการ"); // อัปเดตสถานะด้วยการเข้าถึงข้อมูลใน arraylist<Report> ด้วย index ที่ report ที่ต้องการอยู่ และอัปเดตสถานะ
        dataSource.writeData(reportList); // เขียนไฟล์ลงไป เพื่อบันทักสถานะของ report ( report.status )
        statusLabel.setText(report.getStatus());
        reportList = reportList.filterBy(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.isCategory(officer.getCategory());
            }
        });
    }

    public void handleSolvedButton(){
        reportList = dataSource.readData(); // นำข้อมูลทั้งหมดมา และเขียนลงไปเพื่อข้อมูลใหม่จะไม่ไปลบข้อมูลเก่าออกไป
        String headline = headLineLabel.getText(); // หา headline จาก String
        Report report = reportList.find(headline); // หา report ที่มี headline ตรงกันกับ headline ของ report
        report.updateStatus("เสร็จสิ้น");
//        int index = reportList.getAllReport().indexOf(report); // หา index ของ report
//        reportList.getAllReport().get(index).updateStatus("เสร็จสิ้น"); // อัปเดตสถานะด้วยการเข้าถึงข้อมูลใน arraylist<Report> ด้วย index ที่ report ที่ต้องการอยู่ และอัปเดตสถานะ
        dataSource.writeData(reportList); // เขียนไฟล์ลงไป เพื่อบันทักสถานะของ report ( report.status )
        statusLabel.setText(report.getStatus());

        reportList = reportList.filterBy(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.isCategory(officer.getCategory());
            }
        });
    }

    /*
    method เลือกหมวดหมู่ใน ChoiceBox
     */
    public void handleSelectedCategory(ActionEvent actionEvent) {
        String selected_category = categoryChoiceBox.getValue();
        ReportList reportsListCategory = reportList.filterBy(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.isCategory(selected_category);
            }
        });
        /**
         * แก้อันข้างบนด้วย ตรง return น่าจะต้องเอาไปไว้ใน class Report
         * Edited : 11.10.22 แก้เรียบร้อย
         */

        reportList = reportsListCategory; // list report ใหม่ แทน list report เก่า
        reportListView.getItems().clear(); // เคลียข้อมูลใน list view เก่าออก
        showListView(); // แสดงข้อมูลใน list view ด้วยข้อมูล list report ใหม่

//        showOfficerSortCategory(selected_category); // แสดงข้อมูลของเจ้าหน้าที่ที่รับผิดชอบหมวดหมู่นี่ทั้งหมด

        reportList = dataSource.readData(); // อ่านข้อมูล reportList อันเดิม ( ทั้งหมด )

        /*
        filter ข้อมูลที่ officer รับผิดชอบ
         */
        reportList = reportList.filterBy(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.isCategory(officer.getCategory());
            }
        });

        clearSelectReport(); // เคลีย Label ก่อนเลือกหมวดหมู่อื่น ๆ
        clearShowOfficerSortCategory(); // เคลีย TextArea ของเจ้าหน้าที่

        // ถ้าเลือกหมวดหมู่ทั้งหมด จะแสดงหมวดหมู่ทั้งหมด
        if (selected_category.equals("หมวดหมู่ทั้งหมด")) {
            reportListView.getItems().clear();
            showListView();
            clearSelectReport();
        }
    }

    public void showOfficerSortCategory() {
        String selected_category = categoryLabel.getText();
        officerList = dataSourceOfficerList.readData();
        officerList = officerList.filterBy(new Filterer<Officer>() {
            @Override
            public boolean filter(Officer officer) {
                return officer.isCategory(selected_category);
            }
        });

        String line = "";
        for (String username : officerList.getAllOfficers()) {
            Officer found = officerList.findOfficer(username);
            line = line + found.getName() + "\n";
        }

        officerTextArea.setText(line);
    }

    public void clearShowOfficerSortCategory() {
        officerTextArea.clear();
    }

    public void handleLogoutButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_login_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า student_create_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }
}
