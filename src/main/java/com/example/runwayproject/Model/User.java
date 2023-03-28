package com.example.runwayproject.Model;

public class User {
    public User() {

    }

    public enum Roles {ADMIN, AM, ATC}

    //variables
    private String username;
    private String password;
    private Roles roles;

    //getter setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    //constructor

    public User(String username, String password, Roles roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
