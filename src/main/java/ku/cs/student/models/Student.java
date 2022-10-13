package ku.cs.student.models;

public class Student extends User{
    private boolean active;


    public Student(String name, String username, String password,String imagePath){
        super(name, username, password, imagePath);
        active = true;
    }

    public Student(String name, String username, String password,String imagePath, String latestLoginDate){
        super(name, username, password, imagePath, latestLoginDate);
        active = true;
    }



    public void banned(){
        active = false;
    }

    public void unban(){
        active = true;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Student{" + getName();
    }
}
