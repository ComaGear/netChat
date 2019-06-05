package com.tomcat.netChat.service;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.repository.dao.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class UserService {

    public static User user(Integer id) throws IOException {
        SqlSession openSession = openSession();
        UserMapper userMapper = openSession.getMapper(UserMapper.class);

        User user = userMapper.getUserById(id);

        openSession.close();
        return user;
    }

    public static User user(String email, String password, String userName, String detail) throws IOException {
        SqlSession openSession = openSession();
        UserMapper userMapper = openSession.getMapper(UserMapper.class);

        User user = new User(email, password, userName, detail);
        userMapper.insertUser(user);

        openSession.commit();
        openSession.close();
        return user;
    }

    public static User user(String email, String password) throws IOException {
        SqlSession openSession = openSession();
        UserMapper userMapper = openSession.getMapper(UserMapper.class);

        User user = new User(email, password);
        User existUser = userMapper.identifyUserWithEmailPassword(user);

        openSession.close();
        return existUser;
    }

    public static boolean deleteUser(Integer id) throws IOException {
        SqlSession openSession = openSession();
        UserMapper userMapper = openSession.getMapper(UserMapper.class);

        boolean b = userMapper.deleteUserById(id);

        openSession.commit();
        openSession.close();
        return b;
    }

    private static SqlSession openSession() throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
        return factory.openSession();
    }
}
