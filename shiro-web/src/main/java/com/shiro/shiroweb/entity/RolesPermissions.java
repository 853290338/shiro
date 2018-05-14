package com.shiro.shiroweb.entity;

/**
 * @author denpeng
 * @create 2018-05-14 10:49
 **/
public class RolesPermissions {

  private String roleName;

  private String permission;

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }
}
