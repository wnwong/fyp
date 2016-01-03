package user;

/**
 * Created by User on 2/1/2016.
 */
public class User {
    String username, password, email, location, gender;

    public User(String username, String password, String email, String location, String gender){
        this.username = username;
        this.password = password;
        this.email = email;
        this.location = location;
        this.gender = gender;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getGender() {
        return gender;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.email = "";
        this.location = "";
        this.gender = "";
    }
}
