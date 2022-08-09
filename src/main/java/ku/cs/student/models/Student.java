package ku.cs.student.models;

public class Student {
    private String name;
    private String username;
    private String password;

    private boolean active;


    public Student(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
        active = true;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkUsername(String username){
        if (this.username == username){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkPassword(String password) {
        if (this.password == password){
            return true;
        }
        else{
            return false;
        }

    }

    public String getPassword() {
        return password;
    }

    public boolean changePassword(String oldPassword, String newPassword){
        if (oldPassword == this.password){
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public void getBanned(){

        active = false;
    }

    public boolean isActive() {

        return active;
    }


}
