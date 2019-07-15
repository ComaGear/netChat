package com.tomcat.netChat.service;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.javaBeans.Chat;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.repository.dao.ChatMapper;
import com.tomcat.netChat.repository.dao.GroupChatMapper;
import com.tomcat.netChat.repository.dao.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ChatServiceTest {

//    @Test
//    public void shouldDateFormatWhenTakeGroupChat() throws IOException {
//        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
//        SqlSession openSession = factory.openSession();
//        GroupChatMapper mapper = openSession.getMapper(GroupChatMapper.class);
//        GroupChat group = mapper.getGroupById(1);
//        assertNotNull(group.getDate());
//
//        openSession.close();
//    }
//
//    @Test
//    public void checkGroupChatVariables() throws IOException {
//        List<GroupChat> groupChats = ChatService.allRecordDescription();
//        GroupChat groupChat = groupChats.get(0);
//        assertEquals(new Integer(1), groupChat.getId());
//        assertEquals("testing Chats", groupChat.getGroupName());
//        assertNotNull(groupChat.getDate());
//        assertEquals(new Integer(1), groupChat.getCreator().getId());
//        assertNotNull(groupChat.getDetail());
//    }
//
//    @Test
//    public void takeGroupChatWhenInsert() throws IOException {
//        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
//        SqlSession openSession = factory.openSession();
//        GroupChatMapper mapper = openSession.getMapper(GroupChatMapper.class);
//        GroupChat groupChat = new GroupChat("test", new User(1), "for test group");
//        mapper.insertGroup(groupChat);
//        assertNotNull(groupChat.getId());
//        System.out.println(groupChat.getId());
//
//        openSession.close();
//    }
//
//    @Test
//    public void checkInitializeStructure() throws IOException {
//        boolean b = ChatService.createRecord("test", 1, "test detail");
//
//        assertTrue(b);
//    }
//
//    @Test
//    public void insertChatWithGroupIdUsedMapper() throws IOException {
//        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
//        SqlSession openSession = factory.openSession();
//        ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);
//
//        int i = chatMapper.insertChat(new GroupChat(17), new Chat(new User(1), "hello"));
//
//        assertEquals(1, i);
//    }
//
//    @Test
//    public void insertChatInService() throws IOException {
//        boolean b = ChatService.recordingChat("hello", 17, 1);
//
//        assertTrue(b);
//    }
//
//    @Test
//    public void userCreate() throws IOException {
//        User user = UserService.match("userAbc", "search named abc");
//
//        assertNotNull(user.getId());
//    }
//
//    @Test
//    public void returnedUserDetail() throws IOException {
//        User user = UserService.search(7);
//
//        assertEquals(new Integer(7), user.getId());
//        assertEquals("ComaGear", user.getName());
//        System.out.println(user.getName());
//        assertNotNull(user.getComment());
//    }
//
//    @Test
//    public void returnedUserDetailFromMapper() throws IOException {
//        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
//        SqlSession openSession = factory.openSession();
//        UserMapper userMapper = openSession.getMapper(UserMapper.class);
//
//        User userById = userMapper.getUserById(7);
//
//        assertEquals("userAbc", userById.getName());
//    }
//
//    @Test
//    public void insertAndSelectUserDetail() throws IOException {
//        User user = UserService.match("User1", "this is user1 detail");
//
//        User user1 = UserService.search(user.getId());
//        assertEquals(user.getId(), user1.getId());
//        assertEquals(user.getName(), user1.getName());
//        assertEquals(user.getComment(), user1.getComment());
//    }

}