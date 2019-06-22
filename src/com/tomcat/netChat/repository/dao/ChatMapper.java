package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.Chat;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.javaBeans.User;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;

import java.sql.SQLException;
import java.util.List;

public interface ChatMapper {

    void initializeChat(GroupChat groupChat) throws SQLException;

    List<Chat> getChatByNew(@Param("groupChatId") Integer groupChatId, @Param("obtainAmount") Integer obtainAmount) throws SQLException;

    List<Chat> getChatByAll(Integer groupChatId) throws SQLException;

    int insertChat(@Param("groupChatId") Integer groupChatId, @Param("allChatInRecord") Chat chat) throws SQLException;

    int deleteChat(@Param("groupChatId") GroupChat groupChatId, @Param("allChatInRecord") Chat chat) throws SQLException;
}