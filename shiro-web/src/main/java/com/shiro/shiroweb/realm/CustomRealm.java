package com.shiro.shiroweb.realm;

import com.shiro.shiroweb.dao.UserDao;
import com.shiro.shiroweb.entity.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
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

 /* Map<String, String> map = new HashMap<>();

  {
    map.put("dengpeng", "bf984d7a45678c0240c2fde56bae85ed");
    super.setName("realmName");
  }*/

  @Resource
  private UserDao dao;

  /**
   * 角色授权
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    String username = (String) principalCollection.getPrimaryPrincipal();

    Set<String> roles = getRolesByName(username);
    Set<String> promission = getPromissionByName(roles);

    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.setRoles(roles);
    info.setStringPermissions(promission);
    return info;
  }

  private Set<String> getPromissionByName(Set<String> roles) {
    /**
     * Set<String> set = new HashSet<>();
     *     set.add("user:add");
     */
    Set<String> set = new HashSet<>();
    for (String role : roles) {
      List<String> list = dao.selectPermission(role);
      set.addAll(list);
    }
    return set;
  }

  private Set<String> getRolesByName(String username) {
    /**
     *  Set<String> set = new HashSet<>();
     *     set.add("admin");
     */
    List<String> list = dao.selectRoleByUserName(username);
    Set<String> set = new HashSet<>(list);
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
    User user = dao.selectByUserName(username);

    if (user == null || user.getPassword() == null) {
      return null;
    }

    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user.getPassword(),
        ByteSource.Util.bytes(username),
        "realmName");

    return info;
  }

  public static void main(String[] args) {
    Md5Hash md5Hash = new Md5Hash("123456", "dengpeng");
    System.out.println(md5Hash);
  }
}
