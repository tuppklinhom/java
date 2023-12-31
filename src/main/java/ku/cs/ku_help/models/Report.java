package ku.cs.ku_help.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class Report implements Comparable<Report> {
    private String reporterName;
    private String status; // waiting, pending , solved ประมานนี้มั้ง
    private String headline;
    private String content;
    private String category;

    private String reportedTime;

    private String solution;
    private List<String> likedUserList;



    private int voteCount;

    /*
    Constructor ทุกตัวจะ assign ค่าของข้อมูลหมดทุกตัว
    หมายเหตุ : vote count ค่า default = 0 / status ค่า default = "ดำเนินการ"
     */
    public Report(String reporterName, String headline, String content, String category) {
        this.reporterName = reporterName;
        this.status = "รอดำเนินการ";
        this.content = content;
        this.headline = headline;
        this.category = category;
        this.voteCount = 0;
        this.solution = "ยังไม่ระบุ";
        this.reportedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.likedUserList = new ArrayList<>();
        this.likedUserList.add("-");
    }
    

    public Report(String reporterName, String status, String headline, String content, String category, int voteCount, String reportedTime, String sulution, String likedUserList) {
        this.reporterName = reporterName;
        this.status = status;
        this.content = content;
        this.headline = headline;
        this.category = category;
        this.voteCount = voteCount;
        this.solution = sulution;
        this.reportedTime = reportedTime;
        this.likedUserList = new ArrayList<>();
        Collections.addAll(this.likedUserList, likedUserList.split("\\|"));

    }


    public void addVoteCount(String userName){
        boolean userLikePost = false;
        for (String temp: likedUserList){
            if(userName.equals(temp))
                userLikePost = true;
        }
        if(userLikePost){
            voteCount -=1;
            likedUserList.remove(userName);
            if(voteCount<0)
                voteCount=0;

        }
        else {
            voteCount +=1;
            likedUserList.add(userName);

        }
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

    public String getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        return headline + " [" + voteCount +" vote]" + " " + "[ " + status + " ]";
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

    public boolean isCategory(TreeSet<String> category) {
        return category.contains(this.category);
    }
    public int compareTime(Report o2){
        return this.reportedTime.compareTo(o2.reportedTime);
    }

    public String getTime() {
        return reportedTime;
    }

    public void addSolution(String solution) {
        this.solution = solution;
    }

    public List<String> getLikedUserList() {
        return likedUserList;
    }
    //รอฟังก์ชันอื่น
}
