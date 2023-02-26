package com.jiushi.auth.dao;

import com.jiushi.auth.model.entity.UserDO;
import com.jiushi.auth.model.principal.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengmingyang
 * @version 1.0
 **/
@Repository
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //根据账号查询用户信息
    public UserDO getUserByUsername(String username){
        String sql = "select id,username,password,fullname,mobile from t_user where username = ?";
        //连接数据库查询用户
        List<UserDO> list = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(UserDO.class));
        if(list !=null && list.size()==1){
            return list.get(0);
        }
        return null;
    }

    //根据用户id查询用户权限
    public List<String> findPermissionsByUserId(Long userId){
        String sql = "SELECT tp.url,tr.role_name FROM t_permission  tp JOIN  t_role_permission trp ON tp.id = trp.permission_id JOIN t_role tr on trp.role_id = tr.id JOIN t_user tur on  tur.id = tr.id WHERE tur.id = ?";

        List<PermissionDto> list = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionDto.class));
        List<String> permissions = new ArrayList<>();
        list.forEach(c -> permissions.add(c.getRoleName()));
        return permissions;
    }

    public UserDO getUserByMobilePassword(String mobile, String password) {
        String sql = "select id,username,password,fullname,mobile from t_user where mobile = ?";
        //连接数据库查询用户
        List<UserDO> list = jdbcTemplate.query(sql, new Object[]{mobile}, new BeanPropertyRowMapper<>(UserDO.class));
        if(list !=null && list.size()==1){
            return list.get(0);
        }
        return null;
    }
    public UserDO getUserByMobile(String mobile) {
        String sql = "select id,username,password,fullname,mobile from t_user where mobile = ?";
        //连接数据库查询用户
        List<UserDO> list = jdbcTemplate.query(sql, new Object[]{mobile}, new BeanPropertyRowMapper<>(UserDO.class));
        if(list !=null && list.size()==1){
            return list.get(0);
        }
        return null;
    }
}
