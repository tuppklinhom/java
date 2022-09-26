package ku.cs.student.models;

import java.util.Comparator;

public class Report implements Comparable<Report> {
    private String reporterName;
    private String status; // waiting, pending , solved ประมานนี้มั้ง
    private String content;

    private String headline;

    private String category;

    private int voteCount;

    public Report(String reporterName,String headline,String content, String category){// for use to create noe report
        this.content = content;
        this.reporterName = reporterName;
        this.headline = headline;
        this.category = category;
        this.voteCount = 0;
        status = "waiting";
    }

    public Report(String reporterName, String headline, String content,String category , int voteCount) { // for read data
        this.reporterName = reporterName;
        this.voteCount = voteCount;
        this.content = content;
        this.headline = headline;
        this.category = category;
        status = "waiting";
    }

    public Report(String reporterName, String headline, String content,String category , int voteCount, String status) { // for read fata
        this.reporterName = reporterName;
        this.status = status;
        this.content = content;
        this.headline = headline;
        this.voteCount = voteCount;
        this.category = category;
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

    //รอฟังก์ชันอื่น

}
