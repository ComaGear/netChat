package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.Chat;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.javaBeans.User;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface ChatMapper {

    void initializeChat(GroupChat groupChat);

    List<Chat> getChatByNew(GroupChat groupChat, Integer obtainAmount);

    List<Chat> getChatByAll(GroupChat groupChat);

    int insertChat(GroupChat groupChat, Chat chat);

    int deleteChat(GroupChat groupChat, Chat chat);
}