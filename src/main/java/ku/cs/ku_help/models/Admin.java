package ku.cs.ku_help.models;


public class Admin {

    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isUsername(String username) {
        return this.username.equals(username);
    }

    public boolean isPassword(String password) {
        return this.password.equals(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void changePassword(String newpassword){
        this.password = newpassword;
    }
}
