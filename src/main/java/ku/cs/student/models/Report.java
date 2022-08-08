package ku.cs.student.models;

public class Report {
    private String reporterName;
    private String status; // waiting, pending , solved ประมานนี้มั้ง
    private String content;

    private String headline;

    private int voteCount;

    public Report(String reporter,String headline,String content){
        this.content = content;
        this.reporterName = reporter;
        this.headline = headline;
        status = "waiting";
    }

    public void addVoteCount(){
        voteCount += 1;
    }

    public void reduceVoteCount(){
        voteCount -= 1;
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
        return reporterName + " [" + voteCount +" vote]";
    }

    //รอฟังก์ชันอื่น

}
