package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.Chat;
import com.tomcat.netChat.javaBeans.User;

import java.util.List;

public interface ChatMapper {

    public boolean initializeChat();

    public List<Chat> getChatByNew(Integer obtainAmount);

    public List<Chat> getChatByAll();

    public boolean insertChat(Chat chat);

    public boolean deleteChat(Integer id);
}
