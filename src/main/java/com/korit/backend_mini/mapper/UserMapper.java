package com.korit.backend_mini.mapper;

import com.korit.backend_mini.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> getUserByUserId(Integer userId);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);
    int addUser(User user);
    int changePassword(User user);
    int changeUsername(User user);
}
