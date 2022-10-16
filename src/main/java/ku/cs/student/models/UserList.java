package ku.cs.student.models;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;

    public UserList(){
        users = new ArrayList<>();
    }
    public int size(){
        return users.size();
    }

    public void addAllUser(StudentList studentList){
        for(String name: studentList.getAllStudent()){
            User now = studentList.findStudent(name);
            users.add(now);
        }
    }

    public void addAllUser(OfficerList officerList) {
        for (String name : officerList.getAllOfficers()) {
            Officer now = officerList.findOfficer(name);
            users.add(now);
        }
    }
    public ArrayList<User> getUsers(){
        return users;
    }
}
