package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserMapper {

    boolean initializeUserTable();

    User getUserById(Integer id);

    boolean insertUser(User user);

    boolean deleteUserById(Integer id);
}
