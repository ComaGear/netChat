package com.tomcat.netChat.service;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.repository.dao.UserMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.sql.SQLException;

public class UserService {

    public static User search(String email) throws IOException, UserException {
        User user;
        try (SqlSession openSession = openSession()) {
            UserMapper userMapper = openSession.getMapper(UserMapper.class);

            user = userMapper.getUserByEmail(email);
        }
        if (user == null) throw new UserException(UserException.NOT_EXIST_EXCEPTION_CODE);
        return user;
    }

    public static void join(String email, String password, String userName) throws IOException, UserException {
        try (SqlSession openSession = openSession()) {
            UserMapper userMapper = openSession.getMapper(UserMapper.class);

            User user = new User();
            user.setEmail(email);
            user.setName(userName);
            user.setUserPassword(password);
            userMapper.insertUser(user);

            openSession.commit();
        } catch (PersistenceException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                if (((SQLException) cause).getErrorCode() == 1062) {
                    throw new UserException(UserException.JOIN_EXCEPTION_CODE);
                } else cause.printStackTrace();
            } else e.printStackTrace();
        }
    }

    public static User match(String email, String password) throws IOException, UserException {
        User matchUser = null;
        try (SqlSession openSession = openSession()) {
            UserMapper userMapper = openSession.getMapper(UserMapper.class);

            matchUser = userMapper.identifyUserWithEmailPassword(new User(email, password));
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        if (matchUser == null) throw new UserException(UserException.NOT_EXIST_EXCEPTION_CODE);
        return matchUser;
    }

    public static boolean update(User user) throws IOException {
        int i = 0;
        try (SqlSession openSession = openSession()) {
            UserMapper userMapper = openSession.getMapper(UserMapper.class);

            i = userMapper.updateUser(user);

            openSession.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return i > 0;
    }

    public static boolean delete(String email) throws IOException {
        int i = 0;
        try (SqlSession openSession = openSession()) {
            UserMapper userMapper = openSession.getMapper(UserMapper.class);

            i = userMapper.deleteUserByEmail(email);

            openSession.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return i > 0;
    }

    private static SqlSession openSession() throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
        return factory.openSession();
    }
}
