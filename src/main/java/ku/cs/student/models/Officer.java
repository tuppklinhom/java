package ku.cs.student.models;

import java.util.Set;
import java.util.TreeSet;

public class Officer extends User{
    private String name;
    private String username;
    private String password;

    private String pictureProfile;

    /*
    Date : 29.09.2022 >> เพิ่ม field ชื่อ category เอาไว้บอกว่า หมวดหมู่ของ Officer คนนี้รับผิดชอบหมวดหมู่อะไรบ้าง
     */
    private TreeSet<String>  category;

    public Officer(String name, String username, String password, String pictureProfile) {
        super(name, username, password, pictureProfile);
        this.category = new TreeSet<String>();
    }


    public TreeSet<String> getCategory() {
        return category;
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


}
