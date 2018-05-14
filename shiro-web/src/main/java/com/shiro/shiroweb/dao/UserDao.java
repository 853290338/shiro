package com.shiro.shiroweb.dao;

import com.shiro.shiroweb.entity.User;
import java.util.List;

/**
 * @author denpeng
 * @create 2018-05-14 10:54
 **/
public interface UserDao {

  User selectByUserName(String username);

  List<String> selectRoleByUserName(String username);

  List<String> selectPermission(String role);
}
