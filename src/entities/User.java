package entities;

import java.io.Serializable;

public class User implements Serializable{
    
    private String email;
    private String password;
    private UserType type;

    public User(String email, String pass, UserType type) {
        this.email = email;
        this.password = pass;
        this.type = type;
    }

    public User(String email, String pass) {
        this.email = email;
        this.password = pass;
    }

    public User() {}

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return this.type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

}
