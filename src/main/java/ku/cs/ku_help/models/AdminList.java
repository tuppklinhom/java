package ku.cs.ku_help.models;

import java.util.ArrayList;

public class AdminList {
    private ArrayList<Admin> admins;

    public AdminList(){
        admins = new ArrayList<>();
    }

    public ArrayList<Admin> getAllAdmin() { return admins;}

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public Admin findByUsername(String username) {
        for (Admin admin : admins) {
            if (admin.isUsername(username)) {
                return admin;
            }
        }
        return null;
    }

    public int size(){
        return admins.size();
    }

    public Admin indexOf(int num){
        return admins.get(num);
    }

    public void changePassword(String username, String password) {
        findByUsername(username).changePassword(password);
    }
}
