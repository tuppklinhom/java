package ku.cs.student.models;

import java.util.Comparator;

public class Report implements Comparable<Report> {
    private String reporterName;
    private String status; // waiting, pending , solved ประมานนี้มั้ง
    private String headline;
    private String content;
    private String category;

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
    }

    public Report(String reporterName, String headline, String content, String category, int voteCount) {
        this.reporterName = reporterName;
        this.status = "ดำเนินการ";
        this.content = content;
        this.headline = headline;
        this.category = category;
        this.voteCount = voteCount;
    }

    public Report(String reporterName, String status, String headline, String content, String category) {
        this.reporterName = reporterName;
        this.status = status;
        this.content = content;
        this.headline = headline;
        this.category = category;
        this.voteCount = 0;
    }

    public Report(String reporterName, String status, String headline, String content, String category, int voteCount) {
        this.reporterName = reporterName;
        this.status = status;
        this.content = content;
        this.headline = headline;
        this.category = category;
        this.voteCount = voteCount;
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

    //รอฟังก์ชันอื่น
}
