package ku.cs.student.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Report implements Comparable<Report> {
    private String reporterName;
    private String status; // waiting, pending , solved ประมานนี้มั้ง
    private String headline;
    private String content;
    private String category;

    private String reportedTime;


    private int voteCount;

    /*
    Constructor ทุกตัวจะ assign ค่าของข้อมูลหมดทุกตัว
    หมายเหตุ : vote count ค่า default = 0 / status ค่า default = "ดำเนินการ"
     */
    public Report(String reporterName, String headline, String content, String category) {
        this.reporterName = reporterName;
        this.status = "ดำเนินการ";
        this.content = content;
        this.headline = headline;
        this.category = category;
        this.voteCount = 0;
        this.reportedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    

    public Report(String reporterName, String status, String headline, String content, String category, int voteCount, String reportedTime) {
        this.reporterName = reporterName;
        this.status = status;
        this.content = content;
        this.headline = headline;
        this.category = category;
        this.voteCount = voteCount;
        this.reportedTime = reportedTime;

    }

    public void addVoteCount(){
        voteCount += 1;
    }

    public String getCategory(){
        return category;
    }

    public String getStatus() {
        return status;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getContent() {
        return content;
    }

    public String getHeadline(){
        return headline;
    }

    public String getReporterName() {
        return reporterName;
    }

    @Override
    public String toString() {
        return headline + " [" + voteCount +" vote]";
    }

    @Override
    public int compareTo(Report o) {
        return voteCount - o.voteCount;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public String getReportedTime() {
        return reportedTime;
    }

    public boolean isReporter(String name){
        return this.reporterName.equals(name);
    }
    public boolean isStatus(String status){
        return this.status.equals(status);
    }
    public boolean isCategory(String category) {
        return this.category.equals(category);
    }
    public int compareTime(Report o2){
        return this.reportedTime.compareTo(o2.reportedTime);
    }

    public String getTime() {
        return reportedTime;
    }


    //รอฟังก์ชันอื่น
}
