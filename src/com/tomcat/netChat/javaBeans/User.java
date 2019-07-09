package com.tomcat.netChat.javaBeans;

public class User {

    public static final String TEMPLATE_VARIABLE = "user";
    public static final String TEMPLATE_VARIABLE_COLLECTION = "userList";

    public static final String NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "userEmail";

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
