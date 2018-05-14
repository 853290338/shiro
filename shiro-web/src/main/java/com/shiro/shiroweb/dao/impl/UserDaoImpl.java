package com.shiro.shiroweb.dao.impl;

import com.shiro.shiroweb.dao.UserDao;
import com.shiro.shiroweb.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * @author denpeng
 * @create 2018-05-14 10:54
 **/
@Component
public class UserDaoImpl implements UserDao {

  @Resource
  private JdbcTemplate jdbcTemplate;

  @Override
  public User selectByUserName(String username) {
    String sql = "select * from users where username = ?";

    List<User> users = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<User>() {
      @Override
      public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUserName(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
      }
    });
    if (CollectionUtils.isEmpty(users)) {
      return null;
    }
    return users.get(0);
  }

  @Override
  public List<String> selectRoleByUserName(String username) {
    String sql = "select * from user_roles where username = ?";
    return jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
      @Override
      public String mapRow(ResultSet resultSet, int i) throws SQLException {

        return resultSet.getString("role_name");
      }
    });
  }

  @Override
  public List<String> selectPermission(String role) {

    String sql = "select * from roles_permissions where role_name = ?";
    return jdbcTemplate.query(sql, new String[]{role}, new RowMapper<String>() {
      @Override
      public String mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString("permission");
      }
    });
  }
}
