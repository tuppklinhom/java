package ku.cs.student.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportList {

    private ArrayList<Report> reports;

    public ReportList(){
        reports = new ArrayList<>();
    }
//    public Report indexOf(int num){
//        return reports.get(num);
//    }
//
//    public int size(){
//        return reports.size();
//    }
    public void addReport(Report newReport){
        reports.add(newReport);
    }

    public Report findReport(Report report){
        Report found = null;
        for (Report now : reports.toArray(new Report[0])){
            if (now.equals(report)){
                found = now;
            }
        }

        if(found == null){
            throw new RuntimeException(found + " not found in reportList");
        }
        return found;
    }// for finding report in arraylist



    public ArrayList<Report> getAllReport(){
        return reports;
    }

}
