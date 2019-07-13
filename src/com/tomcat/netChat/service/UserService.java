package com.tomcat.netChat.service;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.repository.dao.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.sql.SQLException;

public class UserService {

    public static User search(String email) throws IOException, UserException {
        SqlSession openSession = null;
        User user;
        try {
            openSession = openSession();
            UserMapper userMapper = openSession.getMapper(UserMapper.class);

            user = userMapper.getUserByEmail(email);

        } finally {
            openSession.close();
        }

        if (user == null) {
            throw new UserException(UserException.NOT_EXIST_EXCEPTION_CODE);
        } else {
            return user;
        }
    }

    public static boolean join(String email, String password, String userName) throws IOException, UserException {
        SqlSession openSession = null;
        try {
            openSession = openSession();
            UserMapper userMapper = openSession.getMapper(UserMapper.class);

            User user = new User();
            user.setEmail(email);
            user.setName(userName);
            user.setUserPassword(password);
            userMapper.insertUser(user);

            openSession.commit();
            return true;
        } catch (SQLException sqlE) {
            int errorCode = sqlE.getErrorCode();
            if (errorCode == 1062) throw new UserException(UserException.JOIN_EXCEPTION_CODE);

        } finally {
            if (openSession != null) openSession.close();
        }
        return false;
    }

    public static User match(String email, String password) throws IOException, UserException {
        SqlSession openSession = null;
        User matchUser = null;
        try {
            openSession = openSession();
            UserMapper userMapper = openSession.getMapper(UserMapper.class);

            matchUser = userMapper.identifyUserWithEmailPassword(new User(email, password));
        } catch (SQLException e) {
            System.out.println("MySQL error Code is " + e.getErrorCode());
            e.printStackTrace();
        } finally {
            if (openSession != null) openSession.close();
        }

        if (matchUser == null) {
            throw new UserException(UserException.LOGIN_EXCEPTION_CODE);
        } else {
            return matchUser;
        }
    }

    public static boolean update(User user) {
        SqlSession openSession = null;
        try {
            openSession = openSession();
            UserMapper userMapper = openSession.getMapper(UserMapper.class);

            int i = userMapper.updateUser(user);

            openSession.commit();
            return i != 0;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (openSession != null) openSession.close();
        }
        return false;
    }

    public static boolean delete(String email) throws IOException {
        SqlSession openSession = null;
        try {
            openSession = openSession();
            UserMapper userMapper = openSession.getMapper(UserMapper.class);

            int b = userMapper.deleteUserByEmail(email);

            openSession.commit();
            return b != 0;
        } finally {
            if (openSession != null) openSession.close();
        }
    }

    private static SqlSession openSession() throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
        return factory.openSession();
    }
}
