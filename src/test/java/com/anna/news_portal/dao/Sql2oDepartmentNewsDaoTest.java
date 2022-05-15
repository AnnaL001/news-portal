package com.anna.news_portal.dao;

import com.anna.news_portal.models.*;
import com.anna.news_portal.parameter_resolvers.DepartmentNewsParameterResolver;
import com.anna.news_portal.parameter_resolvers.DepartmentParameterResolver;
import com.anna.news_portal.parameter_resolvers.TopicParameterResolver;
import com.anna.news_portal.parameter_resolvers.UserParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DepartmentNewsParameterResolver.class)
@ExtendWith(UserParameterResolver.class)
@ExtendWith(DepartmentParameterResolver.class)
@ExtendWith(TopicParameterResolver.class)
class Sql2oDepartmentNewsDaoTest {
  private static Sql2oDepartmentNewsDao departmentNewsDao;
  private static Sql2oUserDao userDao;
  private static Sql2oDepartmentDao departmentDao;
  private static Sql2oTopicDao topicDao;
  private static Connection connection;

  @BeforeAll
  static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", "anna", "pol1234");
    departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
    userDao = new Sql2oUserDao(sql2o);
    departmentDao = new Sql2oDepartmentDao(sql2o);
    topicDao = new Sql2oTopicDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a department news post's data can be added")
  public void add_addsDepartmentNews_true(DepartmentNews departmentNews) {
    departmentNewsDao.add(departmentNews);
    assertTrue(departmentNewsDao.getAll().contains(departmentNews));
  }

  @Test
  @DisplayName("Test that a department news post is provided with a database generated id")
  public void add_setsDepartmentNewsId_true(DepartmentNews departmentNews) {
    int initialId = departmentNews.getId();
    departmentNewsDao.add(departmentNews);
    assertNotEquals(initialId, departmentNews.getId());
  }

  @Test
  @DisplayName("Test that a list of departments news posts can be retrieved")
  public void getAll_returnsDepartmentNewsPostsList_true(DepartmentNews departmentNews) {
    departmentNewsDao.add(departmentNews);
    DepartmentNews[] departmentNewsList = {departmentNews};
    assertEquals(Arrays.asList(departmentNewsList), departmentNewsDao.getAll());
  }

  @Test
  @DisplayName("Test that an empty list is returned if there are no department news posts listed")
  public void getAll_returnsEmptyListIfNoDepartmentNewsPost_true() {
    assertEquals(0, departmentNewsDao.getAll().size());
  }

  @Test
  @DisplayName("Test that a department news post's data can be retrieved")
  public void get_returnsDepartmentNewsPost_true(DepartmentNews departmentNews) {
    departmentNewsDao.add(departmentNews);
    DepartmentNews foundNewsPost = departmentNewsDao.get(departmentNews.getId());
    assertEquals(departmentNews, foundNewsPost);
  }

  @Test
  @DisplayName("Test that a department news post's data can be updated")
  public void update_updatesDepartmentNewsData_true(DepartmentNews departmentNews, User user, Department department) {
    departmentNewsDao.add(departmentNews);
    userDao.add(user);
    departmentDao.add(department);

    // Update added department news post
    departmentNews.setTitle("Incoming Department Head");
    departmentNews.setContent("Handling all things related to company's information systems and software");
    departmentNews.setUser_id(user.getId());
    departmentNews.setDepartment_id(department.getId());
    departmentNewsDao.update(departmentNews);

    // Retrieve updated department news
    DepartmentNews updatedNewsPost = departmentNewsDao.get(departmentNews.getId());
    assertEquals(departmentNews, updatedNewsPost);
  }

  @Test
  @DisplayName("Test that a department post topics can be added")
  public void addTopics_addsDepartmentNewsTopics_true(DepartmentNews departmentNews, Topic topic) {
    departmentNewsDao.add(departmentNews);
    Topic topic1 = new Topic("Information Security");
    Topic[] topics = {topic, topic1};
    departmentNewsDao.addTopics(departmentNews, Arrays.asList(topics));
    assertEquals(Arrays.asList(topics), departmentNewsDao.getTopics(departmentNews.getId()));
  }

  @Test
  @DisplayName("Test that a department news post topics can be retrieved")
  public void getTopics_returnsDepartmentNewsTopics_true(DepartmentNews departmentNews, Topic topic) {
    departmentNewsDao.add(departmentNews);
    Topic topic1 = new Topic("Information Security");
    Topic[] topics = {topic, topic1};
    departmentNewsDao.addTopics(departmentNews, Arrays.asList(topics));
    assertEquals(2, departmentNewsDao.getTopics(departmentNews.getId()).size());
  }

  @Test
  @DisplayName("Test that a department news post can be deleted")
  public void delete_deletesDepartmentNewsPost_false(DepartmentNews departmentNews) {
    departmentNewsDao.add(departmentNews);
    departmentNewsDao.delete(departmentNews.getId());
    assertFalse(departmentNewsDao.getAll().contains(departmentNews));
  }

  @Test
  @DisplayName("Test that department news posts can be cleared in the database")
  public void deleteAll_deletesAllDepartmentNewsPosts_true(DepartmentNews departmentNews) {
    departmentNewsDao.add(departmentNews);
    departmentNewsDao.deleteAll();
    assertEquals(0, departmentNewsDao.getAll().size());
  }

  @AfterEach
  public void tearDown() {
    departmentNewsDao.deleteAll();
    userDao.deleteAll();
    departmentDao.deleteAll();
    topicDao.deleteAll();
  }

  @AfterAll
  static void afterAll() {
    connection.close();
  }
}