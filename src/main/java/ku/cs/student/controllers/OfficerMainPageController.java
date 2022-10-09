package ku.cs.student.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.student.models.Filterer;
import ku.cs.student.models.Report;
import ku.cs.student.models.ReportList;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.ReportListFileDataSource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeSet;

public class OfficerMainPageController {

    @FXML
    private ListView<Report> reportListView;

    @FXML
    private Label statusLabel;

    @FXML
    private Label solutionLabel;

    @FXML
    private Label reporterNameLabel;

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

    public void initialize(){
        dataSource = new ReportListFileDataSource("data","Report.csv");
        reportList = dataSource.readData();
        showListView();
        showCategoryChoiceBox();
        clearSelectReport();
        handleSelectedListView();
    }
    private void showListView(){
        reportListView.getItems().addAll(reportList.getAllReport());
        reportListView.refresh();

        /*
         *มาลบ comment นี้ทีหลัง : โค้ดนี้อาจมี comment ไว้เผื่ออาจจะต้องมาใช้ในอนาคต
         */

//        String category = categoryChoiceBox.getValue();
//        ReportList reportsListCategory = reportList;
//        reportsListCategory.filterCategory(new Filterer<Report>() {
//            @Override
//            public boolean filter(Report report) {
//                return report.getCategory().equals(category);
//            }
//        });
//
//        reportList = reportsListCategory;
//        clearSelectReport();
//        reportListView.getItems().addAll(reportList.getAllReport());
//        reportListView.refresh();
    }

    /*
    method ที่จะแสดงข้อมูลที่เราสามารถเลือกได้จาก ChoiceBox ( ข้อมูลที่เลือกได้มาจาก category ของ Class Report )
     */
    private void showCategoryChoiceBox() {
        ArrayList<String> categoryListForChoiceBox = new ArrayList<String>();
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
                    }
                });
    }

    private void showSelectedReport(Report report){
        reporterNameLabel.setText(report.getReporterName());
        headLineLabel.setText(report.getHeadline());
        solutionLabel.setText(report.getContent());
        statusLabel.setText(report.getStatus());
    }

    public void clearSelectReport(){
        reporterNameLabel.setText("");
        headLineLabel.setText("");
        solutionLabel.setText("");
        statusLabel.setText("");
        categoryLabel.setText("");
    }

    public void handlePendingButton(){
        String headline = headLineLabel.getText(); // หา headline จาก String
        Report report = reportList.find(headline); // หา report ที่มี headline ตรงกันกับ headline ของ report
        int index = reportList.getAllReport().indexOf(report); // หา index ของ report
        reportList.getAllReport().get(index).updateStatus("ดำเนินการ"); // อัปเดตสถานะด้วยการเข้าถึงข้อมูลใน arraylist<Report> ด้วย index ที่ report ที่ต้องการอยู่ และอัปเดตสถานะ
        dataSource.writeData(reportList); // เขียนไฟล์ลงไป เพื่อบันทักสถานะของ report ( report.status )
        statusLabel.setText("ดำเนินการ");
    }

    public void handleSolvedButton(){
        String headline = headLineLabel.getText(); // หา headline จาก String
        Report report = reportList.find(headline); // หา report ที่มี headline ตรงกันกับ headline ของ report
        int index = reportList.getAllReport().indexOf(report); // หา index ของ report
        reportList.getAllReport().get(index).updateStatus("เสร็จสิ้น"); // อัปเดตสถานะด้วยการเข้าถึงข้อมูลใน arraylist<Report> ด้วย index ที่ report ที่ต้องการอยู่ และอัปเดตสถานะ
        dataSource.writeData(reportList); // เขียนไฟล์ลงไป เพื่อบันทักสถานะของ report ( report.status )
        statusLabel.setText("เสร็จสิ้น");
    }

    /*
    method เลือกหมวดหมู่ใน ChoiceBox
     */
    public void handleSelectedCategory(ActionEvent actionEvent) {
        String selected_category = categoryChoiceBox.getValue();
        ReportList reportsListCategory = reportList.filterBy(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.getCategory().equals(selected_category);
            }
        });
        /**
         * แก้อันข้างบนด้วย ตรง return น่าจะต้องเอาไปไว้ใน class Report
         *
         */

        reportList = reportsListCategory; // list report ใหม่ แทน list report เก่า
        reportListView.getItems().clear(); // เคลียข้อมูลใน list view เก่าออก
        showListView(); // แสดงข้อมูลใน list view ด้วยข้อมูล list report ใหม่
        reportList = dataSource.readData(); // อ่านข้อมูล reportList อันเดิม ( ทั้งหมด )
        clearSelectReport(); // เคลีย Label ก่อนเลือกหมวดหมู่อื่น ๆ
        categoryLabel.setText(selected_category);

        // ถ้าเลือกหมวดหมู่ทั้งหมด จะแสดงหมวดหมู่ทั้งหมด
        if (selected_category.equals("หมวดหมู่ทั้งหมด")) {
            reportListView.getItems().clear();
            showListView();
            clearSelectReport();
            categoryLabel.setText(selected_category);
        }
    }
}
