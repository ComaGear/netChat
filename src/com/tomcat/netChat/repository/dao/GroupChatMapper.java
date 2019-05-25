package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.GroupChat;

import java.util.List;

public interface GroupChatMapper {

    void initializeGroup();

    int insertGroup(GroupChat group);

    GroupChat getGroupById(Integer id);

    GroupChat getGroupByName(String name);

    List<GroupChat> getGroupByUser(Integer id);

    List<GroupChat> getGroupByAll();

    int deleteGroupById(Integer id);
}
