package ku.cs.student.models;

import java.util.TreeSet;

public class Officer {
    private String name;
    private String username;
    private String password;

    /*
    Date : 29.09.2022 >> เพิ่ม field ชื่อ category เอาไว้บอกว่า หมวดหมู่ของ Officer คนนี้รับผิดชอบหมวดหมู่อะไรบ้าง
     */
    private TreeSet<String> category;

    public Officer(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.category = new TreeSet<String>();
    }

    public String getName(){return name;}

    public String getUsername() {return username;}

    public TreeSet<String> getCategory() {
        return category;
    }

    public String getPassword(){return password;}

    /*
    method เอาไว้เพิ่มหมวดหมู่ที่ Officer รับผิดชอบ
     */
    public void addCategory(String new_category) {
        category.add(new_category);
    }
}
