package ku.cs.student.service;

import ku.cs.student.models.StudentList;
import ku.cs.student.models.ReportList;
public interface DataSource<T> {
    T readData();

    void writeData(T t);
}
