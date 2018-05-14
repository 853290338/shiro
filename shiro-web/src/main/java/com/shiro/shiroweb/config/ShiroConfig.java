package com.shiro.shiroweb.config;

import com.shiro.shiroweb.realm.CustomRealm;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
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
    return securityManager;
  }

  @Bean
  public Realm myRealm() {
    CustomRealm realm = new CustomRealm();
    realm.setCredentialsMatcher(hashedCredentialsMatcher());
    return realm;
  }

  @Bean
  public HashedCredentialsMatcher hashedCredentialsMatcher() {
    HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
    matcher.setHashAlgorithmName("md5");
    matcher.setHashIterations(1);
    return matcher;
  }

}
