package com.wxq.dao;

import com.wxq.domain.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    Long save(User user);

    void saveUserRoleRe(Long user, Long[] roleIds);

    void delUserRoleRe(Long userId);

    void del(Long userId);




}
