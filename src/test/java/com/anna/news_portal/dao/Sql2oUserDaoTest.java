package com.anna.news_portal.dao;

import com.anna.news_portal.models.Department;
import com.anna.news_portal.models.User;
import com.anna.news_portal.parameter_resolvers.DepartmentParameterResolver;
import com.anna.news_portal.parameter_resolvers.UserParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UserParameterResolver.class)
@ExtendWith(DepartmentParameterResolver.class)
class Sql2oUserDaoTest {
  private static Sql2oUserDao userDao;
  private static Sql2oDepartmentDao departmentDao;
  private static Connection connection;

  @BeforeAll
  static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", "anna", "pol1234");
    userDao = new Sql2oUserDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a user can be added")
  public void add_addsUser_true(User user) {
    userDao.add(user);
    assertTrue(userDao.getAll().contains(user));
  }

  @Test
  @DisplayName("Test that a user's is set to a database generated one upon insert")
  public void add_setsUserId_true(User user) {
    int initialId = user.getId();
    userDao.add(user);
    assertNotEquals(initialId, user.getId());
  }

  @Test
  @DisplayName("Test that a list of users can be retrieved from the database")
  public void getAll_returnsUsersList_true(User user) {
    userDao.add(user);
    User[] users = {user};
    assertEquals(Arrays.asList(users), userDao.getAll());
  }

  @Test
  @DisplayName("Test that empty list is returned if no users in database")
  public void getAll_returnsEmptyListIfNoDatabase_true() {
    assertEquals(0, userDao.getAll().size());
  }

  @Test
  @DisplayName("Test that a user's data can be retrieved")
  public void get_returnsAUser_true(User user) {
    userDao.add(user);
    User foundUser = userDao.get(user.getId());
    assertEquals(user, foundUser);
  }

  @Test
  @DisplayName("Test that a user's data can be updated")
  public void update_updatesUserData_true(User user, Department department) {
    userDao.add(user);
    departmentDao.add(department);
    // Update user data
    user.setName("Jenny Doe");
    user.setPosition("Information Security Analyst");
    user.setDepartment_id(department.getId());
    userDao.update(user);

    // Retrieved updated user
    User updatedUser = userDao.get(user.getId());
    assertEquals(user, updatedUser);
  }

  @Test
  @DisplayName("Test that a user's data can be deleted")
  public void delete_deletesAUser_false(User user) {
    userDao.add(user);
    userDao.delete(user.getId());
    assertFalse(userDao.getAll().contains(user));
  }

  @Test
  @DisplayName("Test that all users' data can be cleared")
  public void deleteAll_deletesAllUsers_true(User user) {
    userDao.add(user);
    userDao.deleteAll();
    assertEquals(0, userDao.getAll().size());
  }

  @AfterEach
  public void tearDown() {
    userDao.deleteAll();
  }

  @AfterAll
  static void afterAll() {
    connection.close();
  }
}