package ku.cs.ku_help.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private String name;
    private String username;
    private String password;
    private String imagePath;
    private String latestLoginDate;

    public User(String name, String username, String password,String imagePath){
        this.name = name;
        this.username = username;
        this.password = password;
        this.imagePath = imagePath;
        latestLoginDate = "-";

    }

    public User(String name, String username, String password, String imagePath, String latestLoginDate) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.imagePath = imagePath;
        this.latestLoginDate = latestLoginDate;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public boolean isPassword(String password) {
        return this.password.equals(password);
    }
    public boolean isUsername(String username) {
        return this.username.equals(username);
    }

    public void changePassword(String newPassword){
        password = newPassword;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public void setLatestLoginDate() {
        this.latestLoginDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public String getLatestLoginDate() {
        return latestLoginDate;
    }

    public int compareTime(User o2){
        return this.latestLoginDate.compareTo(o2.latestLoginDate);
    }

    @Override
    public String toString() {
        return "[นักเรียน] " + name + "| วันที่เข้าใช้งานล่าสุด : " + latestLoginDate;
    }
}
