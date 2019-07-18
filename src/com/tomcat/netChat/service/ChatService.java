package com.tomcat.netChat.service;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.ChatException;
import com.tomcat.netChat.exception.UserException;
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
import java.sql.SQLException;
import java.util.List;

public class ChatService {

    public static List<GroupChat> allRecordDescription() throws IOException, ChatException {
        List<GroupChat> group = null;
        try (SqlSession openSession = openSession()) {
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);

            group = groupChatMapper.getGroupByAll();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        if (group == null || group.size() <= 0)
            throw new ChatException(ChatException.NOT_EXISTED_GROUPS_CODE);
        return group;
    }

    public static Integer createRecord(String groupName, String detail, User creator) throws IOException, UserException {
        try (SqlSession openSession = openSession()) {
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);
            ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

            GroupChat groupChat = new GroupChat(groupName, creator, detail);
            groupChatMapper.insertGroup(groupChat);
            chatMapper.initializeChat(groupChat);

            openSession.commit();
            return groupChat.getId();
        } catch (PersistenceException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                if (((SQLException) cause).getErrorCode() == 1452) {
                    throw new UserException(UserException.NOT_EXIST_EXCEPTION_CODE);
                } else cause.printStackTrace();
            } else e.printStackTrace();
        }
        return -1;
    }

    public static GroupChat recordDescription(Integer id) throws IOException, ChatException {
        GroupChat groupById = null;
        try (SqlSession openSession = openSession()) {
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);

            groupById = groupChatMapper.getGroupById(id);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

        if (groupById == null) throw new ChatException(ChatException.NOT_EXIST_GROUP_CODE);
        return groupById;
    }

    public static List<GroupChat> recordByUser(String email) throws IOException {
        try (SqlSession openSession = openSession()) {
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);

            return groupChatMapper.getGroupByUserEmail(email);
        }
    }

    public static boolean deleteRecord(Integer id) throws IOException {
        try (SqlSession openSession = openSession()) {
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);

            int i = groupChatMapper.deleteGroupById(id);

            openSession.commit();
            return i > 0;
        }
    }

    public static List<Chat> allChatInRecord(Integer id) throws IOException, ChatException {
        List<Chat> chats = null;
        try (SqlSession openSession = openSession()) {
            ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

            chats = chatMapper.getChatByAll(id);
        } catch (PersistenceException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                if (((SQLException) cause).getErrorCode() == 1146) {
                    throw new ChatException(ChatException.NOT_EXIST_GROUP_CODE);
                } else cause.printStackTrace();
            } else e.printStackTrace();
        }

        if (chats == null || chats.size() == 0) throw new ChatException(ChatException.NOT_EXIST_GROUP_CODE);
        return chats;
    }

    public static boolean recordingChat(String message, Integer groupId, User sender) throws IOException, ChatException {
        int i = 0;
        try (SqlSession openSession = openSession()) {
            ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

            i = chatMapper.insertChat(groupId, new Chat(sender, message));

            openSession.commit();

        } catch (PersistenceException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                if (((SQLException) cause).getErrorCode() == 1146) {
                    throw new ChatException(ChatException.NOT_EXIST_GROUP_CODE);
                } else if (((SQLException) cause).getErrorCode() == 1452) {
                    throw new ChatException(ChatException.CHAT_INSERT_EXCEPTION_CODE);
                } else cause.printStackTrace();
            } else e.printStackTrace();
        }
        return i > 0;
    }

    public static boolean deleteChat(Integer groupId, Integer id) throws IOException, ChatException {
        int i = 0;
        try (SqlSession openSession = openSession()) {
            ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

            i = chatMapper.deleteChat(new GroupChat(groupId), new Chat(id));

            openSession.commit();

        } catch (PersistenceException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                if (((SQLException) cause).getErrorCode() == 1146) {
                    throw new ChatException(ChatException.NOT_EXIST_GROUP_CODE);
                } else cause.printStackTrace();
            } else e.printStackTrace();
        }
        return i > 0;
    }

    private static SqlSession openSession() throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
        return factory.openSession();
    }
}
