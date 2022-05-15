package com.anna.news_portal.models;

public class Admin extends User{
  public static final String ROLE = "Admin";

  public Admin(String name, String position) {
    super(name, position);
    this.role = ROLE;
  }

}
