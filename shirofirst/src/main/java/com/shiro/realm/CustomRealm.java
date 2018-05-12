package com.shiro.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义realm
 *
 * @author denpeng
 * @create 2018-05-12 11:50
 **/
public class CustomRealm extends AuthorizingRealm {

  Map<String, String> map = new HashMap<>();

  {
    map.put("dengpeng", "123456");
  }

  /**
   * 角色授权
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    String username = (String) principalCollection.getPrimaryPrincipal();

    Set<String> roles = getRolesByName(username);
    Set<String> promission = getPromissionByName(username);

    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.setRoles(roles);
    info.setStringPermissions(promission);
    return info;
  }

  private Set<String> getPromissionByName(String username) {
    Set<String> set = new HashSet<>();
    set.add("user:add");
    return set;
  }

  private Set<String> getRolesByName(String username) {
    Set<String> set = new HashSet<>();
    set.add("admin");
    return set;
  }

  /**
   * 登陆认证
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {

    UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    String username = token.getUsername();
    String password = String.valueOf(token.getPassword());

    if (map.get(username) == null) {
      return null;
    }
    if (!map.get(username).equals(password)) {
      return null;
    }

    return new SimpleAuthenticationInfo(username, password, "realmName");
  }
}
