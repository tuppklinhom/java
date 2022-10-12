package ku.cs.student.models;

import java.util.Set;
import java.util.TreeSet;

public class Officer {
    private String name;
    private String username;
    private String password;

    private String pictureProfile;

    /*
    Date : 29.09.2022 >> เพิ่ม field ชื่อ category เอาไว้บอกว่า หมวดหมู่ของ Officer คนนี้รับผิดชอบหมวดหมู่อะไรบ้าง
     */
    private TreeSet<String>  category;

    public Officer(String name, String username, String password, String pictureProfile) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.pictureProfile = pictureProfile;
        this.category = new TreeSet<String>();
    }

    public String getName(){return name;}

    public String getUsername() {return username;}

    public TreeSet<String> getCategory() {
        return category;
    }

    public String getPassword(){return password;}

    public boolean isUsername(String username) {
        return this.username.equals(username);
    }

    public boolean isPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isCategory(String category) {
        return this.category.contains(category);
    }

    /*
    method เอาไว้เพิ่มหมวดหมู่ที่ Officer รับผิดชอบ
     */
    public void addCategory(String new_category) {
        category.add(new_category);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public String getPictureProfilePath() {
        return this.pictureProfile;
    }
}
