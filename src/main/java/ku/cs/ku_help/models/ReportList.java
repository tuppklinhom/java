package ku.cs.ku_help.models;

import java.util.ArrayList;

public class ReportList {

    private ArrayList<Report> reports;

    public ReportList(){
        reports = new ArrayList<>();
    }

    public int size(){
        return reports.size();
    }
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
    } // for finding report in arraylist


    public ArrayList<Report> getAllReport(){
        return reports;
    }


    /*
    method นี้เอาไว้หา Class Report จาก ArrayList<Report> ด้วย headline ที่ตรงกัน
    ปล. @Tupp โค้ดนี้แว่นแก้วสร้างขึ้นมาใหม่ มันมีโค้ดที่เหมือนกัน ( บรรทัดที่ 22 ) แต่แว่นแก้วใช้ไม่ถูกเลยสร้างแบบนี้แทน ถ้าอยากแก้อะไรเพิ่มแก้ได้เลย
     */
    public Report find(String headline) {
        int i = 0;
        for (Report report : getAllReport()) {
            if (report.getHeadline().equals(headline)) {
                break;
            }
            i++;
        }
        return getAllReport().get(i);
    }

    /*
    method filterCategory เอาไว้แยกหมวดหมู่ ( category ) ของข้อมูลใน Class category

    Edit: 9.10.22 แก้ชื่อ เอาไปใช้กับอย่างอื่น
     */
    public ReportList filterBy(Filterer<Report> filterer) {
        ReportList filtered = new ReportList();
        for (Report report : getAllReport()) {
            if (filterer.filter(report)) {
                filtered.addReport(report);
            }
        }
        return filtered;
    }
}
