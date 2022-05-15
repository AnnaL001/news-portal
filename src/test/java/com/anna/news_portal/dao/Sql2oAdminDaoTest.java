package com.anna.news_portal.dao;

import com.anna.news_portal.models.Admin;
import com.anna.news_portal.models.Department;
import com.anna.news_portal.parameter_resolvers.AdminParameterResolver;
import com.anna.news_portal.parameter_resolvers.DepartmentParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AdminParameterResolver.class)
@ExtendWith(DepartmentParameterResolver.class)
class Sql2oAdminDaoTest {
  private static Sql2oAdminDao adminDao;
  private static Sql2oDepartmentDao departmentDao;
  private static Connection connection;

  @BeforeAll
  static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", "anna", "pol1234");
    adminDao = new Sql2oAdminDao(sql2o);
    departmentDao = new Sql2oDepartmentDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that an admin's data can be added")
  public void add_addsAdmin_true(Admin admin) {
    adminDao.add(admin);
    assertTrue(adminDao.getAll().contains(admin));
  }

  @Test
  @DisplayName("Test that an admin is provided with a database generated id")
  public void add_setsAdminId_true(Admin admin) {
    int initialId = admin.getId();
    adminDao.add(admin);
    assertNotEquals(initialId,admin.getId());
  }

  @Test
  @DisplayName("Test that a list of admins can be retrieved")
  public void getAll_returnsAdminsList_true(Admin admin) {
    adminDao.add(admin);
    assertEquals(1, adminDao.getAll().size());
  }

  @Test
  @DisplayName("Test that an empty list is returned if there are no admins listed")
  public void getAll_returnsEmptyListIfNoAdmin_true() {
    assertEquals(0, adminDao.getAll().size());
  }



  @Test
  @DisplayName("Test that an admin's data can be retrieved")
  public void get_returnsADepartment_true(Admin admin) {
    adminDao.add(admin);
    Admin foundAdmin = adminDao.get(admin.getId());
    assertEquals(admin, foundAdmin);
  }

  @Test
  @DisplayName("Test that an admin's data can be updated")
  public void update_updatesAdminData_true(Admin admin, Department department) {
    adminDao.add(admin);
    departmentDao.add(department);

    // Update added department data
    admin.setName("Lucian Doe");
    admin.setPosition("Marketing Analyst");
    admin.setDepartment_id(department.getId());
    adminDao.update(admin);

    // Retrieve updated department
    Admin updatedAdmin = adminDao.get(admin.getId());
    assertEquals(admin, updatedAdmin);
  }

  @Test
  @DisplayName("Test that an admin's data can be deleted")
  public void delete_deletesAnAdmin_false(Admin admin) {
    adminDao.add(admin);
    adminDao.delete(admin.getId());
    assertFalse(adminDao.getAll().contains(admin));
  }

  @Test
  @DisplayName("Test that admins' data can be cleared in the database")
  public void deleteAll_deletesAllAdmins_true(Admin admin) {
    adminDao.add(admin);
    adminDao.deleteAll();
    assertEquals(0, adminDao.getAll().size());
  }



  @AfterEach
  public void tearDown() {
    adminDao.deleteAll();
  }

  @AfterAll
  static void afterAll() {
    connection.close();
  }
}