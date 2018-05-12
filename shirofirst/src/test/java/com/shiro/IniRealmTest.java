package com.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * shiro 配置文件认证
 *
 * @author denpeng
 * @create 2018-05-12 10:07
 **/
public class IniRealmTest {

  @Test
  public void shouldAnswerWithTrue() {

    IniRealm realm = new IniRealm("classpath:user.ini");

    //构建securitymanager环境
    DefaultSecurityManager securityManager = new DefaultSecurityManager();
    securityManager.setRealm(realm);
    //主题提交认证
    SecurityUtils.setSecurityManager(securityManager);
    Subject subject = SecurityUtils.getSubject();

    UsernamePasswordToken token = new UsernamePasswordToken("dengpeng", "123456");
    subject.login(token);

    System.out.println(subject.isAuthenticated());

    subject.checkRoles("admin");

    subject.checkPermission("user:delete");

  }
}
