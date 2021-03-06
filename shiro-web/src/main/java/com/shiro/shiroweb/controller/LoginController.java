package com.shiro.shiroweb.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * denglu
 *
 * @author denpeng
 * @create 2018-05-14 9:43
 **/
@RestController
public class LoginController {

  @RequestMapping("login")
  public String login(@RequestParam("username") String username, @RequestParam("password") String password) {

    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    try {
      subject.login(token);
    } catch (Exception e) {
      return "faile";
    }

    if (!subject.hasRole("admin1")) {
      return "没有权限";
    }

    return "success";
  }


  @RequestMapping("index")
  public ModelAndView inde() {
    return new ModelAndView("index");
  }

  @RequiresRoles("admin")
  @RequestMapping("test")
  public String test() {
    return "test";
  }

  @RequiresRoles("admin1")
  @RequestMapping("test1")
  public String test1() {
    return "test1";
  }
}
