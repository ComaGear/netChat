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
        SqlSession openSession = null;
        List<GroupChat> group;
        try {
            openSession = openSession();
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);

            group = groupChatMapper.getGroupByAll();
            if (group == null || group.size() <= 0) {
                throw new ChatException(ChatException.NOT_EXISTED_GROUPS_CODE);
            } else {
                return group;
            }
        } finally {
            if (openSession != null) openSession.close();
        }
    }

    public static Integer createRecord(String groupName, String detail, User creator) throws IOException, UserException {
        SqlSession openSession = null;
        try {
            openSession = openSession();
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);
            ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

            GroupChat groupChat = new GroupChat(groupName, creator, detail);
            groupChatMapper.insertGroup(groupChat);
            chatMapper.initializeChat(groupChat);

            openSession.commit();
            return groupChat.getId();
        } catch (SQLException sqlE) {
            int errorCode = sqlE.getErrorCode();
            if (errorCode == 1452) {
                throw new UserException(UserException.NOT_EXIST_EXCEPTION_CODE);
            } else {
                sqlE.printStackTrace();
            }
        } finally {
            if (openSession != null) openSession.close();
        }
        return -1;
    }

    public static GroupChat recordDescription(Integer id) throws IOException {
        SqlSession openSession = null;
        try {
            openSession = openSession();
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);

            GroupChat groupById = groupChatMapper.getGroupById(id);

            return groupById;
        } finally {
            if (openSession != null) openSession.close();
        }
    }

    public static List<GroupChat> recordByUser(String email) throws IOException {
        SqlSession openSession = null;
        try {
            openSession = openSession();
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);

            List<GroupChat> groupByUser = groupChatMapper.getGroupByUserEmail(email);

            return groupByUser;
        } finally {
            if (openSession != null) openSession.close();
        }
    }

    public static boolean deleteRecord(Integer id) throws IOException {
        SqlSession openSession = null;
        try {
            openSession = openSession();
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);

            int i = groupChatMapper.deleteGroupById(id);

            openSession.commit();
            return i > 0;
        } finally {
            if (openSession != null) openSession.close();
        }
    }

    public static List<Chat> allChatInRecord(Integer id) throws IOException, ChatException {
        SqlSession openSession = null;
        try {
            openSession = openSession();
            ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

            List<Chat> chats = chatMapper.getChatByAll(id);
            if (chats == null || chats.size() == 0) throw new ChatException(ChatException.NOT_EXIST_GROUP_CODE);

            return chats;
        } catch (PersistenceException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                if (((SQLException) cause).getErrorCode() == 1146) {
                    throw new ChatException(ChatException.NOT_EXIST_GROUP_CODE);
                }
            }
        } finally {
            if (openSession != null) openSession.close();
        }
        return null;
    }

    public static boolean recordingChat(String message, Integer groupId, User sender) throws IOException, ChatException {
        SqlSession openSession = null;
        try {
            openSession = openSession();
            ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

            int i = chatMapper.insertChat(groupId, new Chat(sender, message));

            openSession.commit();
            return i > 0;
        } catch (SQLException sqlE) {
            int errorCode = sqlE.getErrorCode();
            if (errorCode == 1452) {
                throw new ChatException(ChatException.CHAT_INSERT_EXCEPTION_CODE);
            } else if (errorCode == 1146) {
                throw new ChatException(ChatException.NOT_EXIST_GROUP_CODE);
            } else {
                sqlE.printStackTrace();
            }
        } finally {
            openSession.close();
        }
        return false;
    }

    public static boolean deleteChat(Integer groupId, Integer id) throws IOException, ChatException {
        SqlSession openSession = null;
        try {
            openSession = openSession();
            ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

            int i = chatMapper.deleteChat(new GroupChat(groupId), new Chat(id));

            openSession.commit();
            return i > 0;
        } catch (SQLException sqlE) {
            int errorCode = sqlE.getErrorCode();
            if (errorCode == 1146) {
                throw new ChatException(ChatException.NOT_EXIST_GROUP_CODE);
            } else {
                sqlE.printStackTrace();
            }
        } finally {
            if (openSession != null) openSession.close();
        }
        return false;
    }

    private static SqlSession openSession() throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
        return factory.openSession();
    }
}
