package com.tomcat.netChat.javaBeans;

public class User {

    private Integer id;
    private String name;
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public User(Integer id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
    }

    public User(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public User(Integer id) {
        this.id = id;
    }

    public User() {
    }
}
