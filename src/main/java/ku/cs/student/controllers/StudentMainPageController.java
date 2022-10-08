package ku.cs.student.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import ku.cs.student.models.Report;
import ku.cs.student.models.ReportList;
import ku.cs.student.models.Student;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.ReportListFileDataSource;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

public class StudentMainPageController {

    private Student user;
    @FXML
    private ListView<Report> reportListView;
    @FXML
    private Label reporterNameLabel;
    @FXML
    private Label voteCountLabel;
    @FXML
    private Label headlineLabel;
    @FXML
    private TextArea contentTextArea;

    private DataSource<ReportList> dataSource;

    private ReportList reportList;

    private Report tempReportForVote; //สำหรับ ไว้โหวต

    public void initialize(){
        //dataSource = new ReportListHardCodeDataSource();

        dataSource = new ReportListFileDataSource("data", "Report.csv");
        reportList = dataSource.readData();

        user = (Student) com.github.saacsos.FXRouter.getData();
        showListView();
        clearSelectReport();
        handleSelectedListView();
    }
    private void showListView(){
        sort();
        reportListView.getItems().addAll(reportList.getAllReport());
        reportListView.refresh();
    }
    private void sort() {
        Collections.sort(reportList.getAllReport(), new Comparator<Report>() {
            @Override
            public int compare(Report o1, Report o2) {
                return o2.getVoteCount() - o1.getVoteCount();
            }
        });
    }// sort list from ListView max to min

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
    }



    public void clearSelectReport(){
        reporterNameLabel.setText("");
        voteCountLabel.setText("");
        headlineLabel.setText("");
        contentTextArea.setText("");
    }

    public void handleVoteUpButton(){
        reportList.findReport(tempReportForVote).addVoteCount();
        dataSource.writeData(reportList);
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


    // รอ button
}
