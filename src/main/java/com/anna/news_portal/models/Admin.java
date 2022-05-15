package com.anna.news_portal.models;

public class Admin extends User{
  public static final String ROLE = "Admin";

  public Admin(String name, String position, int department_id) {
    super(name, position, department_id);
    this.role = ROLE;
  }

}
