package com.anna.news_portal.models;

import java.util.Objects;

public class User {
  protected int id;
  protected String name;
  protected String position;
  protected String role;
  protected int department_id;

  public static final String ROLE = "Normal user";

  public User(String name, String position) {
    this.name = name;
    this.position = position;
    this.role = ROLE;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public int getDepartment_id() {
    return department_id;
  }

  public void setDepartment_id(int department_id) {
    this.department_id = department_id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return department_id == user.department_id && Objects.equals(name, user.name) && Objects.equals(position, user.position) && Objects.equals(role, user.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, position, role, department_id);
  }
}
