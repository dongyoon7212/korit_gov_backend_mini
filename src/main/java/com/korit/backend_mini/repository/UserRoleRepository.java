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
        try {
            return userRoleMapper.addUserRole(userRole);
        } catch (Exception e) {
            return 0;
        }
    }

    public int updateUserRole(UserRole userRole) {
        return userRoleMapper.updateUserRole(userRole);
    }

}
