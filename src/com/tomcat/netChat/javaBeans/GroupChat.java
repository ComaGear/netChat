package com.tomcat.netChat.javaBeans;

import java.util.Date;

public class GroupChat {

    private Integer id;
    private String groupName;
    private String detail;
    private Date date;
    private User creator;

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

    public void setDate(Date date) {
        this.date = date;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public GroupChat(Integer id, String groupName, String detail, Date date, User creator) {
        this.id = id;
        this.groupName = groupName;
        this.detail = detail;
        this.date = date;
        this.creator = creator;
    }

    public GroupChat() {
    }
}
