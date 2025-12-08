package com.korit.backend_mini.repository;

import com.korit.backend_mini.entity.UserRole;
import com.korit.backend_mini.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepository {

    @Autowired
    private UserRoleMapper userRoleMapper;

    public int addUserRole(UserRole userRole) {
        return userRoleMapper.addUserRole(userRole);
    }

    public void updateUserRole(UserRole userRole) {
        userRoleMapper.updateUserRole(userRole);
    }

}
