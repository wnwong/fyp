package user;

/**
 * Created by User on 2/1/2016.
 */
public class User {
    String username, password, email, location;

    public User(String username, String password, String email, String location){
        this.username = username;
        this.password = password;
        this.email = email;
        this.location = location;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.email = "";
        this.location = "";
    }
}
