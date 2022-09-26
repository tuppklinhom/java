package ku.cs.student.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StudentList {
    /**
     ******* Change Arraylist to Map for storing student **********

     */
    private Map<String, Student> students;

    public StudentList(){
        students = new TreeMap<String, Student>();
    }


    public int size(){
        return students.size();
    }

    public void addStudent(Student newStudent){
        students.put(newStudent.getUsername(), newStudent);
    }

    public Set<String> getAllStudent(){
        return students.keySet();
    }

    public Student findStudent(String username) {
        Student found = students.get(username);
        return found;
    }


}
