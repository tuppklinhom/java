package ku.cs.student.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.student.models.Report;
import ku.cs.student.models.ReportList;
import ku.cs.student.service.DataSource;
import ku.cs.student.service.ReportListFileDataSource;
import ku.cs.student.service.ReportListHardCodeDataSource;

public class StudentMainPageController {

    @FXML
    private ListView<Report> reportListView;
    @FXML
    private Label reporterNameLabel;
    @FXML
    private Label voteCountLabel;
    @FXML
    private Label headlineLabel;
    @FXML
    private Label contentLabel;

    private DataSource<ReportList> dataSource;

    private ReportList reportList;

    private Report tempReportForVote; //สำหรับ ไว้โหวต

    public void initialize(){
        //dataSource = new ReportListHardCodeDataSource();

        dataSource = new ReportListFileDataSource("data", "Report.csv");
        reportList = dataSource.readData();
        showListView();
        clearSelectReport();
        handleSelectedListView();
    }
    private void showListView(){
        reportListView.getItems().addAll(reportList.getAllReport());
        reportListView.refresh();
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
        contentLabel.setText(report.getContent());
    }



    public void clearSelectReport(){
        reporterNameLabel.setText("");
        voteCountLabel.setText("");
        headlineLabel.setText("");
        contentLabel.setText("");
    }

    public void handleVoteUpButton(){
        tempReportForVote.addVoteCount();
        //เดี๋ยวน่าจะทำตัวเช็คด้วยว่าโหวตซ้ำไหม
        showSelectedReport(tempReportForVote);
    }
    // รอ button
}
