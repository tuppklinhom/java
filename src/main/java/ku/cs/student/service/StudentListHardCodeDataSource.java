package ku.cs.student.service;

import ku.cs.student.models.StudentList;
import ku.cs.student.models.Student;

public class StudentListHardCodeDataSource {
    private StudentList studentList;

    public StudentListHardCodeDataSource(){
        studentList = new StudentList();
        readData();
    }

    public void readData() {
        studentList.addStudent(new Student("Tupp", "TuppLnwZa5678", "TuppPassword"));
        studentList.addStudent(new Student("Kwan", "KKwan", "Kwan1234@"));
        studentList.addStudent(new Student("Ya", "YaWantFreeTime", "yey"));
    }

    public StudentList getStudentList(){
        return studentList;
    }
}
