package com.wxq.service.impl;

import com.wxq.dao.RoleDao;
import com.wxq.dao.UserDao;
import com.wxq.domain.Role;
import com.wxq.domain.User;
import com.wxq.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> list() {
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            Long id = user.getId();
            List<Role> roleList = roleDao.findRoleByUserId(id);
            user.setRoles(roleList);
        }
        return userList;
    }

    public void save(User user, Long[] roleIds) {
        Long userId = userDao.save(user);
        userDao.saveUserRoleRe(userId, roleIds);
    }

    public void del(Long userId) {
        userDao.delUserRoleRe(userId);
        userDao.del(userId);

    }

    @Override
    public User login(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, password);
        return user;
    }
}
