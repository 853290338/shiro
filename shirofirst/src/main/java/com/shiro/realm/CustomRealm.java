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
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 自定义realm
 *
 * @author denpeng
 * @create 2018-05-12 11:50
 **/
public class CustomRealm extends AuthorizingRealm {

  Map<String, String> map = new HashMap<>();

  {
    map.put("dengpeng", "bf984d7a45678c0240c2fde56bae85ed");
    super.setName("realmName");
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
    String password = map.get(username);

    if (password == null) {
      return null;
    }

    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, ByteSource.Util.bytes("dengpeng"),
        "realmName");

    return info;
  }

  public static void main(String[] args) {
    Md5Hash md5Hash = new Md5Hash("123456", "dengpeng");
    System.out.println(md5Hash);
  }
}
