package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.Chat;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.javaBeans.User;

import java.util.List;

public interface ChatMapper {

    public boolean initializeChat();

    public List<Chat> getChatByNew(GroupChat groupChat, Integer obtainAmount);

    public List<Chat> getChatByAll(GroupChat groupChat);

    public boolean insertChat(Chat chat);

    public boolean deleteChat(Integer id);
}