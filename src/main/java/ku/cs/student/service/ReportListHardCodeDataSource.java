package ku.cs.student.service;


import ku.cs.student.models.Report;
import ku.cs.student.models.ReportList;



public class ReportListHardCodeDataSource{
    private ReportList reportList;

    public ReportListHardCodeDataSource() {
        reportList = new ReportList();
        readData();
    }

    //*******************************************
    //      น่าจะเป้นอะไรที่ต้องลบที่หลัง ตอนนี้เป้นแบบนี้ไปก่อน
    //
    //
    //
    //********************************************

    public void readData() {
        reportList.addReport(new Report("Tupp", "ขยะถูกทิ้งไม่เป็นที่", "testContent"));
        reportList.addReport(new Report("test2", "something", "something someone along the way"));
        reportList.addReport(new Report("Wow uwu", "just boring", "nothing much, just boring"));
    }

    public ReportList getReportList(){
        return reportList;
    }
}
