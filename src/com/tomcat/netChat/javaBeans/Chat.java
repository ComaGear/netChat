package com.tomcat.netChat.javaBeans;

import java.sql.Timestamp;
import java.util.Date;

public class Chat {

    private Integer id;
    private User sender;
    private Timestamp updateDate;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Chat(Integer id, User sender, Timestamp updateDate, String message) {
        this.id = id;
        this.sender = sender;
        this.updateDate = updateDate;
        this.message = message;
    }

    public Chat(User sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public Chat() {
    }
}
