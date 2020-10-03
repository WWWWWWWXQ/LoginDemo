package com.wxq.dao;


import com.wxq.domain.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();

    void save(Role role);

    List<Role> findRoleByUserId(Long id);
}
