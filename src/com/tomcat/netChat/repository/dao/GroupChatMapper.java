package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.GroupChat;

import java.util.List;

public interface GroupChatMapper {

    public boolean initializeGroup();

    public boolean insertGroup(GroupChat group);

    public GroupChat getGroupById(Integer id);

    public List<GroupChat> getGroupByAll();

    public boolean deleteGroupById(Integer id);
}
