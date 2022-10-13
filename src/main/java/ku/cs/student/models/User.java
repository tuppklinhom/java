package ku.cs.student.models;

public class User {
    private String name;
    private String username;
    private String password;
    private String imagePath;

    public User(String name, String username, String password,String imagePath){
        this.name = name;
        this.username = username;
        this.password = password;
        this.imagePath = imagePath;
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

    public void changePassword(String newPassword){
        password = newPassword;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }


}
