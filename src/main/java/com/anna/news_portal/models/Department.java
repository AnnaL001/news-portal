package com.anna.news_portal.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department {
  private int id;
  private String name;
  private String description;
  private int employeeCount;
  private List<User> users;
  private List<DepartmentNews> news;

  public Department(String name, String description) {
    this.name = name;
    this.description = description;
    this.employeeCount = 0;
    this.users = new ArrayList<>();
    this.news = new ArrayList<>();
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getEmployeeCount() {
    return employeeCount;
  }

  public void setEmployeeCount(int employeeCount) {
    this.employeeCount = employeeCount;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public List<DepartmentNews> getNews() {
    return news;
  }

  public void setNews(List<DepartmentNews> news) {
    this.news = news;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Department that = (Department) o;
    return employeeCount == that.employeeCount && Objects.equals(name, that.name) && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, employeeCount);
  }
}
