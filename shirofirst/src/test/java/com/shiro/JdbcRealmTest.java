package com.shiro;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * jdbc认证
 *
 * @author denpeng
 * @create 2018-05-12 10:39
 **/
public class JdbcRealmTest {

  DruidDataSource source = new DruidDataSource();

  {
    source.setPassword("root");
    source.setUsername("root");
    source.setUrl(
        "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC");
  }

  @Test
  public void shouldAnswerWithTrue() {

    JdbcRealm realm = new JdbcRealm();
    realm.setDataSource(source);
    realm.setPermissionsLookupEnabled(true);

    /**
     * 自定义sql表需自定义查询语句
     */
    //realm.setAuthenticationQuery("select password from test_user where user_name = ?");

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
    subject.checkPermission("user:select");

  }
}
