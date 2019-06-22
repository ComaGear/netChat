package com.tomcat.netChat.javaBeans;

public class User {

    private String email;
    private String password;
    private String name;
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String email, String name, String comment) {
        this.email = email;
        this.name = name;
        this.comment = comment;
    }

    public User(String email, String password, String name, String comment) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.comment = comment;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
    }

    public User() {
    }
}
