package ku.cs.ku_help.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.ku_help.models.*;
import ku.cs.ku_help.service.DataSource;
import ku.cs.ku_help.service.OfficerListFileDataSource;
import ku.cs.ku_help.service.ReportListFileDataSource;

import java.io.File;
import java.io.IOException;
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
    private Label headLineLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private Button selectedCategoryButton;

    @FXML
    private TextField solutionTextField;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private ImageView officerImageView;

    @FXML
    private Label officerNameLabel;

    private Report tempReportForVote; //สำหรับการโหวต

    private DataSource<ReportList> dataSourceReportList;

    private ReportList reportList;


    private Officer officer;

    private DataSource<OfficerList> dataSourceOfficerList;

    private OfficerList officerList;

    @FXML
    private TextArea officerTextArea;

    public void initialize(){
        dataSourceReportList = new ReportListFileDataSource("data","Report.csv");
        reportList = dataSourceReportList.readData();
        officer = (Officer) com.github.saacsos.FXRouter.getData(); // ข้อมูลของเจ้าหน้าที่ที่ล็อกอินเข้ามา โดนส่งผ่าน FXRouter

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
        showStatusChoiceBox();
        showOfficerName();
        showOfficerImage();
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
        categoryChoiceBox.setOnAction(this::handleSelectedCategory);
    }

    private void showStatusChoiceBox() {
        String[] status = {"ดำเนินการ", "เสร็จสิ้น"};
        statusChoiceBox.getItems().addAll(status);
    }
    private void handleSelectedListView() {
        reportListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Report>() {
                    @Override
                    public void changed(ObservableValue<? extends Report> observableValue, Report oldValue, Report newValue) {
                        if (newValue!= null) {
                            System.out.println(newValue + " is selected");
                            showSelectedReport(newValue);
                            showOfficerSortCategory();
                            System.out.println(newValue.getStatus());
                        }
                    }
                });
    }

    private void showOfficerImage() {
        File imageFile = new File(officer.getImagePath());
        officerImageView.setImage(new Image(imageFile.toURI().toString()));
    }

    private void showOfficerName() {
        officerNameLabel.setText(officer.getName());
    }

    private void showSelectedReport(Report report){
        if(report != null){
            headLineLabel.setText(report.getHeadline());
            statusLabel.setText(report.getStatus());
            categoryLabel.setText(report.getCategory());
            contentLabel.setText(report.getContent());
            solutionLabel.setText(report.getSolution());
        }
    }

    public void clearSelectReport(){
        headLineLabel.setText("");
        solutionLabel.setText("");
        statusLabel.setText("");
        categoryLabel.setText("");
        contentLabel.setText("");
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

        clearSelectReport(); // เคลีย Label ก่อนเลือกหมวดหมู่อื่น ๆ
        clearShowOfficerSortCategory(); // เคลีย TextArea ของเจ้าหน้าที่

        reportList = dataSourceReportList.readData(); // อ่านข้อมูล reportList อันเดิม ( ทั้งหมด )

        /*
        filter ข้อมูลที่ officer รับผิดชอบ
         */
        reportList = reportList.filterBy(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.isCategory(officer.getCategory());
            }
        });

        // ถ้าเลือกหมวดหมู่ทั้งหมด จะแสดงหมวดหมู่ทั้งหมด
        if (selected_category.equals("หมวดหมู่ทั้งหมด")) {
            reportListView.getItems().clear();
            showListView();
            clearSelectReport();
            clearShowOfficerSortCategory(); // เคลีย TextArea ของเจ้าหน้าที่
        }

//        categoryChoiceBox.setOnAction(this::handleSelectedCategory);
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

    public void handleAddSolutionAndUpdateStatus(ActionEvent actionEvent) {
        reportList = dataSourceReportList.readData(); // นำข้อมูลทั้งหมดมา และเขียนลงไปเพื่อข้อมูลใหม่จะไม่ไปลบข้อมูลเก่าออกไป
        String solution = solutionTextField.getText();// หา headline จาก String
        String headline = headLineLabel.getText();
        String status = statusChoiceBox.getValue();
        Report report = reportList.find(headline); // หา report ที่มี headline ตรงกันกับ headline ของ report
        if (solution.equals("")){
            solution = "-";
        }
        report.addSolution(solution);
        report.updateStatus(status);
        dataSourceReportList.writeData(reportList); // เขียนไฟล์ลงไป เพื่อบันทักสถานะของ report ( report.status )
        solutionLabel.setText(report.getSolution());


        reportList = dataSourceReportList.readData(); // นำข้อมูลทั้งหมดมา และเขียนลงไปเพื่อข้อมูลใหม่จะไม่ไปลบข้อมูลเก่าออกไป
        solutionTextField.clear();
        reportListView.getItems().clear();
        showSelectedReport(report);

        reportList = reportList.filterBy(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.isCategory(officer.getCategory());
            }
        });
        showListView();
    }

    public void handleChangePasswordPageButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("officer_change_password_page",officer);
        } catch (IOException e) {
            System.err.println("ไปทีหน้า officer change password page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }
}
