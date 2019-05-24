package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.Chat;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.javaBeans.User;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface ChatMapper {

    void initializeChat(GroupChat groupChat);

    List<Chat> getChatByNew(@Param("groupChat") GroupChat groupChat, @Param("obtainAmount") Integer obtainAmount);

    List<Chat> getChatByAll(GroupChat groupChat);

    int insertChat(@Param("groupChat") GroupChat groupChat, @Param("chat") Chat chat);

    int deleteChat(@Param("groupChat") GroupChat groupChat, @Param("chat") Chat chat);
}