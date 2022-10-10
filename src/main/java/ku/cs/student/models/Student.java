package ku.cs.student.models;

public class Student {
    private String name;
    private String username;
    private String password;

    private String imagePath;
    private boolean active;


    public Student(String name, String username, String password,String imagePath){
        this.name = name;
        this.username = username;
        this.password = password;
        this.imagePath = imagePath;
        active = true;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public boolean isPassword(String password) {
        return this.password.equals(password);
    }
    public boolean isUsername(String username) {
        return this.username.equals(username);
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


    public String getImagePath() {
        return imagePath;
    }
}
