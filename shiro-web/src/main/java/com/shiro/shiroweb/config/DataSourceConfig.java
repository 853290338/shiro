package com.shiro.shiroweb.config;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author denpeng
 * @create 2018-05-14 10:49
 **/
@Configuration
public class DataSourceConfig {

  @Bean
  public DataSource dataSource() {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setUrl(
        "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC");
    dataSource.setUsername("root");
    dataSource.setPassword("root");
    return dataSource;
  }
}
