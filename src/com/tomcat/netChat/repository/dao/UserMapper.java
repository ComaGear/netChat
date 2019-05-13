package com.tomcat.netChat.repository.dao;

import com.tomcat.netChat.javaBeans.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserMapper {

    public boolean initializeUserTable();

    public User getUserById(Integer id);

    public boolean insertUser(User user);

    public boolean deleteUserById(Integer id);
}
