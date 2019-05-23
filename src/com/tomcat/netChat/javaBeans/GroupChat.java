package com.tomcat.netChat.javaBeans;

import java.sql.Timestamp;
import java.util.Date;

public class GroupChat {

    private Integer id;
    private String groupName;
    private Timestamp date;
    private User creator;
    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public GroupChat(Integer id, String groupName, Timestamp date, User creator, String detail) {
        this.id = id;
        this.groupName = groupName;
        this.date = date;
        this.creator = creator;
        this.detail = detail;
    }

    public GroupChat(String groupName, User creator, String detail) {
        this.groupName = groupName;
        this.creator = creator;
        this.detail = detail;
    }

    public GroupChat(Integer id) {
        this.id = id;
    }

    public GroupChat() {
    }
}
