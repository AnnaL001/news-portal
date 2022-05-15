package com.anna.news_portal.dao;

import com.anna.news_portal.models.Department;
import com.anna.news_portal.parameter_resolvers.DepartmentParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DepartmentParameterResolver.class)
class Sql2oDepartmentDaoTest {
  private static Connection connection;
  private static Sql2oDepartmentDao departmentDao;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", "anna", "pol1234");
    departmentDao = new Sql2oDepartmentDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a department's data can be added to the database")
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


  @AfterEach
  public void tearDown() {
    departmentDao.deleteAll();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}