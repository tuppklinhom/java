package ku.cs.ku_help.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.ku_help.models.Filterer;
import ku.cs.ku_help.models.Officer;
import ku.cs.ku_help.models.Report;
import ku.cs.ku_help.service.OfficerListFileDataSource;
import ku.cs.ku_help.service.ReportListFileDataSource;

import javax.swing.text.LabelView;
import java.io.IOException;

public class CreatorPageController {
    @FXML
    private Label createrLabel;

    public void initialize(){
        showLabel();
    }

    public void showLabel() {
        createrLabel.setText("Creater");
    }

    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_login_page");
        } catch (IOException e) {
            System.err.println("ไปทีหน้า login page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }

    }
}
