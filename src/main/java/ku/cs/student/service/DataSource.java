package ku.cs.student.service;

public interface DataSource<T> {
    T readData();

    void writeData(T t);

}
