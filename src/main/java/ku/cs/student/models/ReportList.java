package ku.cs.student.models;

import java.util.ArrayList;

public class ReportList {

    private ArrayList<Report> reports;

    public ReportList(){
        reports = new ArrayList<>();
    }

    public void addReport(Report newReport){
        reports.add(newReport);
    }

    public ArrayList<Report> getAllReport(){
        return reports;
    }
}
