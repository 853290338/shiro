package com.shiro;

import com.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 自定义realm认证
 *
 * @author denpeng
 * @create 2018-05-12 12:03
 **/
public class CustomRealmTest {

  @Test
  public void shouldAnswerWithTrue() {

    CustomRealm realm = new CustomRealm();

    //构建securitymanager环境
    DefaultSecurityManager securityManager = new DefaultSecurityManager();
    securityManager.setRealm(realm);

    HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
    matcher.setHashAlgorithmName("md5");
    matcher.setHashIterations(1);
    realm.setCredentialsMatcher(matcher);
    //主题提交认证
    SecurityUtils.setSecurityManager(securityManager);
    Subject subject = SecurityUtils.getSubject();

    UsernamePasswordToken token = new UsernamePasswordToken("dengpeng", "123456");
    subject.login(token);

    System.out.println(subject.isAuthenticated());

//    subject.checkRoles("admin");
//
//    subject.checkPermission("user:add");

  }
}
