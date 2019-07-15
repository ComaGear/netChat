package com.tomcat.netChat.service;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.repository.dao.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class UserServiceTest {

    @Test
    public void insertUserRecordToResource() {
        String email = "coma.gear921@gmail.com";
        String password = "1234";
        String name = "ComaGear";

        SqlSession openSession = null;
        try {
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
            openSession = build.openSession();
            UserMapper mapper = openSession.getMapper(UserMapper.class);
            mapper.initializeUserTable();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            UserService.join(email, password, name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void thatUserInfoShouldMatch() {
        String email = "coma.gear921@gmail.com";
        String password = "1234";
        try {
            User match = UserService.match(email, password);
            System.out.println(match.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UserException e) {
            e.printStackTrace();
        }
    }
}