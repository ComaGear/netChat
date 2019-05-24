package com.tomcat.netChat.service;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.javaBeans.Chat;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.repository.dao.ChatMapper;
import com.tomcat.netChat.repository.dao.GroupChatMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;

public class ChatService {

    public static List<GroupChat> groupChatList() throws IOException {
        SqlSession openSession = openSession();
        GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);

        List<GroupChat> group = groupChatMapper.getGroupByAll();

        openSession.close();
        return group;
    }

    public static boolean createGroupChat(String groupName, Integer creatorId, String detail) throws IOException {
        SqlSession openSession = openSession();
        GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);
        ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

        GroupChat groupChat = new GroupChat(groupName, new User(creatorId), detail);
        groupChatMapper.insertGroup(groupChat);
        chatMapper.initializeChat(groupChat);

        boolean b;
        try {
            chatMapper.getChatByAll(groupChat);
            b = true;
        } catch (PersistenceException e) {
            b = false;
            e.printStackTrace();
        }
        openSession.commit();
        openSession.close();

        return b;
    }

    public static GroupChat groupChat(Integer id) throws IOException {
        SqlSession openSession = openSession();
        GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);
        GroupChat groupById = groupChatMapper.getGroupById(id);

        return groupById;
    }

    public static List<Chat> chat(Integer id) throws IOException {
        SqlSession openSession = openSession();
        GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);
        ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

        GroupChat group = groupChatMapper.getGroupById(id);
        List<Chat> chats = chatMapper.getChatByAll(group);

        openSession.close();
        return chats;
    }

    public static boolean chat(String message, Integer groupId, Integer senderId) throws IOException {
        SqlSession openSession = openSession();
        ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

        int i = chatMapper.insertChat(new GroupChat(groupId), new Chat(new User(senderId), message));

        openSession.commit();
        openSession.close();
        return i != 0;
    }

    private static SqlSession openSession() throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
        return factory.openSession();
    }
}
