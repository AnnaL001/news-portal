package com.anna.news_portal.models;

import com.anna.news_portal.parameter_resolvers.DepartmentNewsParameterResolver;
import com.anna.news_portal.parameter_resolvers.DepartmentParameterResolver;
import com.anna.news_portal.parameter_resolvers.UserParameterResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DepartmentParameterResolver.class)
@ExtendWith(UserParameterResolver.class)
@ExtendWith(DepartmentNewsParameterResolver.class)
class DepartmentTest {
  @Test
  @DisplayName("Test that a Department class instance is instantiated correctly")
  public void newDepartment_instantiatesCorrectly_true(Department department) {
    assertNotNull(department);
  }

  @Test
  @DisplayName("Test that a Department class instance instantiates with a  department name")
  public void newDepartment_instantiatesWithName(Department department) {
    assertEquals("Information Technology", department.getName());
  }

  @Test
  @DisplayName("Test that a Department class instance instantiates with a description")
  public void newDepartment_instantiatesWithDescription_true(Department department) {
    assertEquals("A department that manages information systems used and devised by the company", department.getDescription());
  }

  @Test
  @DisplayName("Test that a Department class instance instantiates a default Id")
  public void newDepartment_instantiatesWithDefaultId_true(Department department) {
    assertEquals(0, department.getId());
  }

  @Test
  @DisplayName("Test that a Department class instance instantiates with default employee count")
  public void newDepartment_instantiatesWithDefaultEmployeeCount_true(Department department) {
    assertEquals(0, department.getEmployee_count());
  }

  @Test
  @DisplayName("Test that a Department class instance instantiates with empty users list")
  public void newDepartment_instantiatesWithEmptyUsersList_true(Department department) {
    assertEquals(0, department.getUsers().size());
  }

  @Test
  @DisplayName("Test that a Department class instance instantiates with empty news list")
  public void newDepartment_instantiatesWithEmptyNewsList_true(Department department) {
    assertEquals(0, department.getNews().size());
  }

  @Test
  @DisplayName("Test that department ID is set as specified")
  public void setId_setsIdCorrectly_true(Department department) {
    department.setId(1);
    assertEquals(1, department.getId());
  }

  @Test
  @DisplayName("Test that department name is set as specified")
  public void setName_setsNameCorrectly_true(Department department) {
    department.setName("Finance");
    assertEquals("Finance", department.getName());
  }

  @Test
  @DisplayName("Test that description is set as specified")
  public void setDescription_setsDescriptionCorrectly_true(Department department) {
    department.setDescription("Manage company's financial related matters");
    assertEquals("Manage company's financial related matters", department.getDescription());
  }

  @Test
  @DisplayName("Test that employee count is set as specified")
  public void setEmployee_count_setsEmployeeCountCorrectly_true(Department department, User user) {
    department.getUsers().add(user);
    department.setEmployee_count();
    assertEquals(1, department.getEmployee_count());
  }

  @Test
  @DisplayName("Test that the users list is set as specified")
  public void setUsers_setsUsersListCorrectly_true(Department department, User user) {
    List<User> users = new ArrayList<>(List.of(user));
    department.setUsers(users);
    assertEquals(users, department.getUsers());
  }

  @Test
  @DisplayName("Test that news list is set as specified")
  public void setNews_setNewsListCorrectly_true(Department department, DepartmentNews departmentNews) {
    List<DepartmentNews> departmentNewsList = new ArrayList<>(List.of(departmentNews));
    department.setNews(departmentNewsList);
    assertEquals(departmentNewsList, department.getNews());
  }

  @Test
  @DisplayName("Test that Department class instances are regarded as same if having same property values")
  public void equals_returnsTrueIfSameProperties_true(Department department) {
    Department department1 = new Department("Information Technology", "A department that manages information systems used and devised by the company");
    assertEquals(department, department1);
    assertEquals(department.hashCode(), department1.hashCode());
  }
}