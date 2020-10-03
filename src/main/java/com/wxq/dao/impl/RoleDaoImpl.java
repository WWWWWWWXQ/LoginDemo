package com.wxq.dao.impl;

import com.wxq.dao.RoleDao;
import com.wxq.domain.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Transactional
public class RoleDaoImpl implements RoleDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Role> findAll() {
        List<Role> roleList = jdbcTemplate.query("select * from sys_role", new BeanPropertyRowMapper<Role>(Role.class));
        return roleList;
    }


    public void save(Role role) {
        jdbcTemplate.update("insert into sys_role values (?,?,?)"
                ,null
                ,role.getRoleName()
                ,role.getRoleDesc());
    }

    public List<Role> findRoleByUserId(Long id) {
        List<Role> roleList = jdbcTemplate.query("select * from sys_user_role ur, sys_role r where ur.roleId=r.id and ur.userId = ?"
                , new BeanPropertyRowMapper<Role>(Role.class), id);
        return roleList;
    }

    @Override
    public void delRoleUserRe(Long roleId) {
        jdbcTemplate.update("delete from sys_user_role where roleId = ? ", roleId);
    }

    @Override
    public void del(Long roleId) {
        jdbcTemplate.update("delete from sys_role where id = ?", roleId);
    }


}
