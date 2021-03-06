package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

public interface UserMapper {

    boolean initializeUserTable();

    User getUserByEmail(String email);

    User identifyUserWithEmailPassword(User user);

    boolean insertUser(User user);

    int updateUser(User user);

    int deleteUserByEmail(String email);
}
