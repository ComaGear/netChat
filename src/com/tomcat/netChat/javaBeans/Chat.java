package com.tomcat.netChat.javaBeans;

import java.util.Date;

public class Chat {

    private Integer id;
    private User sender;
    private GroupChat group;
    private Date updateDate;
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User senderId) {
        this.sender = senderId;
    }

    public GroupChat getGroup() {
        return group;
    }

    public void setGroup(GroupChat group) {
        this.group = group;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Chat(Integer id, User sender, GroupChat group, Date updateDate, String message) {
        this.id = id;
        this.sender = sender;
        this.group = group;
        this.updateDate = updateDate;
        this.message = message;
    }
}
