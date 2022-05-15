package com.anna.news_portal.dao;

import com.anna.news_portal.models.Department;
import com.anna.news_portal.models.DepartmentNews;
import com.anna.news_portal.models.User;
import com.anna.news_portal.parameter_resolvers.DepartmentNewsParameterResolver;
import com.anna.news_portal.parameter_resolvers.DepartmentParameterResolver;
import com.anna.news_portal.parameter_resolvers.UserParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DepartmentParameterResolver.class)
@ExtendWith(UserParameterResolver.class)
@ExtendWith(DepartmentNewsParameterResolver.class)
class Sql2oDepartmentDaoTest {
  private static Connection connection;
  private static Sql2oDepartmentDao departmentDao;
  private static Sql2oUserDao userDao;
  private static Sql2oDepartmentNewsDao departmentNewsDao;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", "anna", "pol1234");
    departmentDao = new Sql2oDepartmentDao(sql2o);
    userDao = new Sql2oUserDao(sql2o);
    departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a department's data can be added")
  public void add_addsDepartment_true(Department department) {
    departmentDao.add(department);
    assertTrue(departmentDao.getAll().contains(department));
  }

  @Test
  @DisplayName("Test that a department is provided with a database generated id")
  public void add_setsDepartmentId_true(Department department) {
    int initialId = department.getId();
    departmentDao.add(department);
    assertNotEquals(initialId, department.getId());
  }

  @Test
  @DisplayName("Test that a list of departments can be retrieved")
  public void getAll_returnsDepartmentsList_true(Department department) {
    departmentDao.add(department);
    assertEquals(1, departmentDao.getAll().size());
  }

  @Test
  @DisplayName("Test that an empty list is returned if there are no departments listed")
  public void getAll_returnsEmptyListIfNoDepartment_true() {
    assertEquals(0, departmentDao.getAll().size());
  }

  @Test
  @DisplayName("Test that a department's data can be retrieved")
  public void get_returnsADepartment_true(Department department) {
    departmentDao.add(department);
    Department foundDepartment = departmentDao.get(department.getId());
    assertEquals(department, foundDepartment);
  }

  @Test
  @DisplayName("Test that a department's data can be updated")
  public void update_updatesDepartmentData_true(Department department) {
    departmentDao.add(department);
    // Update added department data
    department.setName("IT");
    department.setDescription("Handle all things related to company's information systems and software");
    departmentDao.update(department);

    // Retrieve updated department
    Department updatedDepartment = departmentDao.get(department.getId());
    assertEquals(department.getName(), updatedDepartment.getName());
    assertEquals(department.getDescription(), updatedDepartment.getDescription());
  }

  @Test
  @DisplayName("Test that a list of a department's users can be retrieved")
  public void getUsers_returnsDepartmentUsers_true(Department department, User user) {
    departmentDao.add(department);
    user.setDepartment_id(department.getId());
    userDao.add(user);

    assertTrue(departmentDao.getUsers(department.getId()).contains(user));
  }

  @Test
  @DisplayName("Test that a list of a department's news posts can be retrieved")
  public void getNews_returnsDepartmentNews_true(Department department, DepartmentNews departmentNews) {
    departmentDao.add(department);
    departmentNews.setDepartment_id(department.getId());
    departmentNewsDao.add(departmentNews);

    assertTrue(departmentDao.getNews(department.getId()).contains(departmentNews));
  }

  @Test
  @DisplayName("Test that a department's data can be deleted")
  public void delete_deletesADepartment_false(Department department) {
    departmentDao.add(department);
    departmentDao.delete(department.getId());
    assertFalse(departmentDao.getAll().contains(department));
  }

  @Test
  @DisplayName("Test that departments' data can be cleared in the database")
  public void deleteAll_deletesAllDepartments_true(Department department) {
    departmentDao.add(department);
    departmentDao.deleteAll();
    assertEquals(0, departmentDao.getAll().size());
  }

  @AfterEach
  public void tearDown() {
    departmentDao.deleteAll();
    userDao.deleteAll();
    departmentNewsDao.deleteAll();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}