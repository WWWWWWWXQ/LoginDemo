package com.wxq.service.impl;

import com.wxq.dao.RoleDao;
import com.wxq.domain.Role;
import com.wxq.service.RoleService;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> list() {
        List<Role> roleList = roleDao.findAll();
        return roleList;
    }

    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public void del(Long roleId) {
        roleDao.delRoleUserRe(roleId);
        roleDao.del(roleId);
    }
}
