package ku.cs.student.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.student.models.*;
import ku.cs.student.service.CategoryListFileDataSource;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.ReportListFileDataSource;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
    @FXML
    private ComboBox<String> voteSortComboBox;
    @FXML
    private ComboBox<String> dateSortComboBox;
    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView profileImageView;
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
        usernameLabel.setText(user.getName());

        showProfile();
        showListView();
        showCategoryComboBox();
        showStatusComboBox();
        showVoteSortComboBox();
        showDateSortComboBox();
        clearSelectReport();
        handleSelectedListView();
    }
    private void showListView(){
        reportListView.getItems().clear();
        reportListView.getItems().addAll(reportList.getAllReport());
        reportListView.refresh();
    }
   private void showProfile(){
        File imageFile = new File(user.getImagePath());
        profileImageView.setImage(new Image(imageFile.toURI().toString()));
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
    private void showVoteSortComboBox(){
        String[] choice = {"มากไปน้อย", "น้อยไปมาก"};
        voteSortComboBox.getItems().addAll(choice);
        voteSortComboBox.setOnAction(this::handleVoteSortComboBox);
    }
    private void showDateSortComboBox(){
        String[] choice = {"เก่าที่สุด" , "ใหม่ที่สุด"};
        dateSortComboBox.getItems().addAll(choice);
        dateSortComboBox.setOnAction(this::handleDateSortComboBox);
    }

    private void handleDateSortComboBox(ActionEvent actionEvent) {
        String choice = dateSortComboBox.getValue();
        if(choice.equals("ใหม่ที่สุด")){
            Collections.sort(reportList.getAllReport(), new Comparator<Report>() {
                @Override
                public int compare(Report o1, Report o2) {
                    return o2.compareTime(o1);
                }
            });
        }
        else {
            Collections.sort(reportList.getAllReport(), new Comparator<Report>() {
                @Override
                public int compare(Report o1, Report o2) {
                    return o1.compareTime(o2);
                }
            });
        }
        showListView();
    }

    private void handleVoteSortComboBox(ActionEvent actionEvent) {
        String sort = voteSortComboBox.getValue();

        if (sort == null || sort.equals("มากไปน้อย")){
            Collections.sort(reportList.getAllReport(), new Comparator<Report>() {
                @Override
                public int compare(Report o1, Report o2) {
                    return o2.getVoteCount() - o1.getVoteCount();
                }
            });
        }
        else{
            Collections.sort(reportList.getAllReport(), new Comparator<Report>() {
                @Override
                public int compare(Report o1, Report o2) {
                    return o1.getVoteCount() - o2.getVoteCount();
                }
            });
        }
        showListView();
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


        showListView();
    }






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
