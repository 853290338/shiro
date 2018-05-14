package com.shiro.shiroweb.config;

import com.shiro.shiroweb.realm.CustomRealm;
import com.shiro.shiroweb.session.CustomSessionManager;
import com.shiro.shiroweb.session.SessionDao;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置
 *
 * @author denpeng
 * @create 2018-05-14 9:10
 **/
@Configuration
public class ShiroConfig {

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    bean.setSecurityManager(securityManager);

    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
    filterChainDefinitionMap.put("/index.html", "anon");
    filterChainDefinitionMap.put("/login", "anon");
    filterChainDefinitionMap.put("/index", "anon");
    filterChainDefinitionMap.put("/**", "authc");
    bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

    bean.setLoginUrl("/longin");

    return bean;
  }

  @Bean
  public SecurityManager securityManager() {
    DefaultSecurityManager securityManager = new DefaultWebSecurityManager();//web项目需要DefaultWebSecurityManager
    securityManager.setRealm(myRealm());
    securityManager.setSessionManager(sessionManager());
    return securityManager;
  }

  @Bean
  public Realm myRealm() {
    CustomRealm realm = new CustomRealm();
    realm.setCredentialsMatcher(hashedCredentialsMatcher());
    return realm;
  }

  //密码校验规则
  @Bean
  public HashedCredentialsMatcher hashedCredentialsMatcher() {
    HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
    matcher.setHashAlgorithmName("md5");
    matcher.setHashIterations(1);
    return matcher;
  }

  //注解方式开启权限认证 strat
  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
  }
  //注解方式开启权限认证 end

  @Bean
  public SessionManager sessionManager() {
    CustomSessionManager sessionManager = new CustomSessionManager();
    sessionManager.setSessionDAO(sessionDao());
    return sessionManager;
  }

  @Bean
  public SessionDao sessionDao() {
    return new SessionDao();
  }
}
