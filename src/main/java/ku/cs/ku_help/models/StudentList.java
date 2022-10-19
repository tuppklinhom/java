package ku.cs.ku_help.models;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StudentList {
    /**
     ******* Change Arraylist to Map for storing student **********

     */
    private Map<String, User> students;

    public StudentList(){
        students = new TreeMap<String, User>();
    }


    public int size(){
        return students.size();
    }

    public void addStudent(User newStudent){
        students.put(newStudent.getUsername(), newStudent);
    }

    public Set<String> getAllStudent(){
        return students.keySet();
    }

    public User findStudent(String username) {
        User found = students.get(username);
        return found;
    }


}
