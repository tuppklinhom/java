package ku.cs.ku_help.service;

public interface DataSource<T> {
    T readData();

    void writeData(T t);
}
