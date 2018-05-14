package com.shiro.shiroweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ShiroWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShiroWebApplication.class, args);
  }
}
