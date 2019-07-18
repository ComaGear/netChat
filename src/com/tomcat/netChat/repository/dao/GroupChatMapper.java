package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.GroupChat;

import java.sql.SQLException;
import java.util.List;

public interface GroupChatMapper {

    void initializeGroup();

    int insertGroup(GroupChat group);

    int updateGroup(GroupChat group);

    GroupChat getGroupById(Integer id);

    GroupChat getGroupByName(String name);

    List<GroupChat> getGroupByUserEmail(String email);

    List<GroupChat> getGroupByAll();

    int deleteGroupById(Integer id);
}
