package com.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

  SimpleAccountRealm realm = new SimpleAccountRealm();

  @Before
  public void addUser() {
    realm.addAccount("dengpeng", "123456", "admin", "user");
  }

  /**
   * Rigorous Test :-)
   */
  @Test
  public void shouldAnswerWithTrue() {

    //构建securitymanager环境

    DefaultSecurityManager securityManager = new DefaultSecurityManager();
    securityManager.setRealm(realm);

    //主题提交认证
    SecurityUtils.setSecurityManager(securityManager);
    Subject subject = SecurityUtils.getSubject();

    UsernamePasswordToken token = new UsernamePasswordToken("dengpeng", "123456");
    subject.login(token);

    System.out.println(subject.isAuthenticated());

    subject.checkRoles("admin", "user");

    subject.logout();

    System.out.println(subject.isAuthenticated());
  }


}
