package ku.cs.student.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.student.models.*;
import ku.cs.student.service.CategoryListFileDataSource;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.ReportListFileDataSource;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class StudentMainPageController {

    private Student user;
    @FXML
    private ListView<Report> reportListView;
    @FXML
    private Label reporterNameLabel;
    @FXML
    private Label reportedDateLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label voteCountLabel;
    @FXML
    private Label headlineLabel;
    @FXML
    private TextArea contentTextArea;
    @FXML
    private CheckBox yourReportCheckBox;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ComboBox<String> statusComboBox;

    private DataSource<ReportList> reportListDataSource;

    private DataSource<CategoryList> categoryListDataSource;

    private CategoryList categoryList;
    private ReportList reportList;

    private Report tempReportForVote; //สำหรับ ไว้โหวต

    public void initialize(){


        reportListDataSource = new ReportListFileDataSource("data", "Report.csv");
        reportList = reportListDataSource.readData();
        categoryListDataSource = new CategoryListFileDataSource("data", "Category.csv");
        categoryList = categoryListDataSource.readData();

        user = (Student) com.github.saacsos.FXRouter.getData();
        showListView();
        showCategoryComboBox();
        showStatusComboBox();
        clearSelectReport();
        handleSelectedListView();
    }
    private void showListView(){
        sortVote();
        reportListView.getItems().addAll(reportList.getAllReport());
        reportListView.refresh();
    }
    private void showCategoryComboBox(){
        categoryComboBox.getItems().addAll(categoryList.getAllCategories());
        categoryComboBox.getItems().add("ทั้งหมด");
        categoryComboBox.setOnAction(this::handleSearchCategoryAndStatusComboBox);
    }

    private void showStatusComboBox(){
        ReportList temp = reportListDataSource.readData();
        Collection<String> status = new HashSet<>();
        for (Report r : temp.getAllReport()){
            status.add(r.getStatus());
        }
        statusComboBox.getItems().addAll(status);
        statusComboBox.getItems().add("ทั้งหมด");
        statusComboBox.setOnAction(this::handleSearchCategoryAndStatusComboBox);

    }

    //เลือก combobox
    private void handleSearchCategoryAndStatusComboBox(ActionEvent actionEvent) {
        // ---- สำหรับเผื่อติ้กเฉพาะ report ของตัวเอง----
        reportList = reportListDataSource.readData();
        if (yourReportCheckBox.isSelected()){
            reportList = reportList.filterBy(new Filterer<Report>() {
                @Override
                public boolean filter(Report report) {
                    return report.isReporter(user.getName());
                }
            });
        }
        else{
            reportList = reportListDataSource.readData();
        }
        // -------------------------------------------

        String status = statusComboBox.getValue();
        String category = categoryComboBox.getValue();

        if (category != null && !category.equals("ทั้งหมด")){
            reportList = reportList.filterBy(new Filterer<Report>() {
                @Override
                public boolean filter(Report report) {
                    return report.isCategory(category);
                }
            });
        }
        if (status != null && !status.equals("ทั้งหมด")){
            reportList = reportList.filterBy(new Filterer<Report>() {
                @Override
                public boolean filter(Report report) {
                    return report.isStatus(status);
                }
            });
        }

        reportListView.getItems().clear();
        showListView();
    }


    private void sortVote() {
        Collections.sort(reportList.getAllReport(), new Comparator<Report>() {
            @Override
            public int compare(Report o1, Report o2) {
                return o2.getVoteCount() - o1.getVoteCount();
            }
        });
    }// sort list from ListView max to min

    public void handleYourReportCheck(ActionEvent actionEvent){
        if(yourReportCheckBox.isSelected()){
            reportList = reportList.filterBy(new Filterer<Report>() {
                @Override
                public boolean filter(Report report) {
                    return report.isReporter(user.getName());
                }
            });
        }else{
            reportList = reportListDataSource.readData();
        }
        reportListView.getItems().clear();
        showListView();
    }
    private void handleSelectedListView() {
        reportListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Report>() {
                    @Override
                    public void changed(ObservableValue<? extends Report> observable, Report oldValue, Report newValue) {
                        System.out.println(newValue + " is selected");
                        showSelectedReport(newValue);
                        tempReportForVote = newValue;
                    }
                }
        );
    }


    private void showSelectedReport(Report report){
        reporterNameLabel.setText(report.getReporterName());
        headlineLabel.setText(report.getHeadline());
        voteCountLabel.setText(String.valueOf(report.getVoteCount()));
        contentTextArea.setText(report.getContent());
        reportedDateLabel.setText(report.getTime());
        statusLabel.setText(report.getStatus());
    }



    public void clearSelectReport(){
        reporterNameLabel.setText("");
        voteCountLabel.setText("");
        headlineLabel.setText("");
        contentTextArea.setText("");
        reportedDateLabel.setText("");
        statusLabel.setText("");
    }

    public void handleVoteUpButton(){
        reportList.findReport(tempReportForVote).addVoteCount();
        reportListDataSource.writeData(reportList);
        reportListView.getItems().clear();
        showListView();
        showSelectedReport(tempReportForVote);
    }

    public void handleNewReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_create_report",user);
        } catch (IOException e) {
            System.err.println("ไปทีหน้า student_create_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }

    }

    public void handleLogoutButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("student_login_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า student_create_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }


}
