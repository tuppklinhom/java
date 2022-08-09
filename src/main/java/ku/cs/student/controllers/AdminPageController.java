package ku.cs.student.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.student.models.Report;
import ku.cs.student.models.ReportList;
import ku.cs.student.service.ReportListHardCodeDataSource;

public class AdminPageController {

    @FXML
    private ListView<Report> reportListView;

    @FXML
    private Label statusLabel;

    @FXML
    private Label solutionLabel;

    @FXML
    private Label reporterNameLabel;

    private ReportListHardCodeDataSource dataSource;

    private ReportList reportList;

    private Report tempReportForVote; //สำหรับ ไว้โหวต

    public void initialize(){
        dataSource = new ReportListHardCodeDataSource();
        reportList = dataSource.getReportList();
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
    }

    public void clearSelectReport(){
        reporterNameLabel.setText("");
    }

    public void handlePendingButton(){
        statusLabel.setText("ดำเนินการ");
    }

    public void handleSolvedButton(){
        statusLabel.setText("เสร็จสิ้น");
    }
}
