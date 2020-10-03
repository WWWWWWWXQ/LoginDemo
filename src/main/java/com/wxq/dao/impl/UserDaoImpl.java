package com.wxq.dao.impl;

import com.wxq.dao.UserDao;
import com.wxq.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


@Transactional
public class UserDaoImpl implements UserDao {
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        List<User> userList = jdbcTemplate.query("select * from sys_user", new BeanPropertyRowMapper<User>(User.class));
        return userList;
    }

    public Long save(final User user) {
//        jdbcTemplate.update("insert into sys_user values(?,?,?,?,?)",
//                null,
//                user.getUsername(),
//                user.getEmail(),
//                user.getPassword(),
//                user.getPhoneNum());

        //创建PreparedStatementCreator
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                //使用原始的jdbc完成原始的PreparedStatement的组件
                PreparedStatement preparedStatement = con.prepareStatement("insert into sys_user values(?,?,?,?,?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1,null);
                preparedStatement.setString(2,user.getUsername());
                preparedStatement.setString(3,user.getEmail());
                preparedStatement.setString(4,user.getPassword());
                preparedStatement.setString(5,user.getPhoneNum());
                return preparedStatement;
            }
        };
        //创建KeyHolder
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //返回当前保存的用户的id， 该id是数据库自己生成的
        jdbcTemplate.update(creator, keyHolder);

        //获得生成的主键
        long userId = keyHolder.getKey().longValue();
        return userId;
    }

    public void saveUserRoleRe(Long userId, Long[] roleIds) {
        for (Long roleId : roleIds) {
            jdbcTemplate.update("insert into sys_user_role values (?,?)", userId, roleId);
        }
    }

    public void delUserRoleRe(Long userId) {
        jdbcTemplate.update("delete from sys_user_role where userId = ? ", userId);
    }

    public void del(Long userId) {
        jdbcTemplate.update("delete from sys_user where id = ?", userId);
    }

}
