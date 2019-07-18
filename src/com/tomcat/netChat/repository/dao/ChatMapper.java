package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.Chat;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.javaBeans.User;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;

import java.sql.SQLException;
import java.util.List;

public interface ChatMapper {

    void initializeChat(GroupChat groupChat);

    List<Chat> getChatByNew(@Param("groupChatId") int groupChatId, @Param("obtainAmount") int obtainAmount);

    List<Chat> getChatByAll(@Param("groupChatId") int groupChatId);

    int insertChat(@Param("groupChatId") int groupChatId, @Param("chat") Chat chat);

    int deleteChat(@Param("groupChatId") GroupChat groupChatId, @Param("allChatInRecord") Chat chat);
}