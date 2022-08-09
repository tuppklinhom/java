package ku.cs.student.models;

import java.util.ArrayList;

public class StudentList {
    private ArrayList<Student> students;

    public StudentList(){
        students = new ArrayList<>();
    }

    public Student indexOf(int num){
        return students.get(num);
    }

    public int size(){
        return students.size();
    }

    public void addStudent(Student newStudent){
        students.add(newStudent);
    }

    public ArrayList<Student> getAllStudent(){
        return students;
    }
}
