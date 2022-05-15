package com.anna.news_portal.dao;


import com.anna.news_portal.models.GeneralNews;
import com.anna.news_portal.models.Topic;
import com.anna.news_portal.models.User;
import com.anna.news_portal.parameter_resolvers.GeneralNewsParameterResolver;
import com.anna.news_portal.parameter_resolvers.TopicParameterResolver;
import com.anna.news_portal.parameter_resolvers.UserParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TopicParameterResolver.class)
@ExtendWith(GeneralNewsParameterResolver.class)
@ExtendWith(UserParameterResolver.class)
class Sql2oGeneralNewsDaoTest {
  private static Sql2oGeneralNewsDao generalNewsDao;
  private static Sql2oUserDao userDao;
  private static Sql2oTopicDao topicDao;
  private static Connection connection;

  @BeforeAll
  static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", "anna", "pol1234");
    generalNewsDao = new Sql2oGeneralNewsDao(sql2o);
    userDao = new Sql2oUserDao(sql2o);
    topicDao = new Sql2oTopicDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a general news post's data can be added")
  public void add_addsGeneralNewsPost_true(GeneralNews generalNews) {
    generalNewsDao.add(generalNews);
    assertTrue(generalNewsDao.getAll().contains(generalNews));
  }

  @Test
  @DisplayName("Test that a general news post is provided with a database generated id")
  public void add_setsGeneralNewsPostId_true(GeneralNews generalNews) {
    int initialId = generalNews.getId();
    generalNewsDao.add(generalNews);
    assertNotEquals(initialId, generalNews.getId());
  }

  @Test
  @DisplayName("Test that a list of general news posts can be retrieved")
  public void getAll_returnsGeneralNewsPostsList_true(GeneralNews generalNews) {
    generalNewsDao.add(generalNews);
    GeneralNews[] generalNewsList = {generalNews};
    assertEquals(Arrays.asList(generalNewsList), generalNewsDao.getAll());
  }

  @Test
  @DisplayName("Test that an empty list is returned if there are no general news posts listed")
  public void getAll_returnsEmptyListIfNoGeneralNewsPost_true() {
    assertEquals(0, generalNewsDao.getAll().size());
  }

  @Test
  @DisplayName("Test that a general news post's data can be retrieved")
  public void get_returnsGeneralNewsPost_true(GeneralNews generalNews) {
    generalNewsDao.add(generalNews);
    GeneralNews foundNewsPost = generalNewsDao.get(generalNews.getId());
    assertEquals(generalNews, foundNewsPost);
  }

  @Test
  @DisplayName("Test that a general news post's data can be updated")
  public void update_updatesGeneralNewsPost_true(GeneralNews generalNews, User user) {
    generalNewsDao.add(generalNews);
    userDao.add(user);

    // Update added general news post
    generalNews.setTitle("Incoming CEO");
    generalNews.setContent("Handling all things related to company's information systems and software");
    generalNews.setUser_id(user.getId());
    generalNewsDao.update(generalNews);

    // Retrieve updated general news
    GeneralNews updatedNewsPost = generalNewsDao.get(generalNews.getId());
    assertEquals(generalNews, updatedNewsPost);
  }

  @Test
  @DisplayName("Test that a general news post topics can be added")
  public void addTopics_addsGeneralNewsTopics_true(GeneralNews generalNews, Topic topic) {
    generalNewsDao.add(generalNews);
    Topic topic1 = new Topic("Information Security");
    Topic[] topics = {topic, topic1};
    generalNewsDao.addTopics(generalNews, Arrays.asList(topics));
    assertEquals(Arrays.asList(topics), generalNewsDao.getTopics(generalNews.getId()));
  }

  @Test
  @DisplayName("Test that a general news post topics can be retrieved")
  public void getTopics_returnsGeneralNewsTopics_true(GeneralNews generalNews, Topic topic) {
    generalNewsDao.add(generalNews);
    Topic topic1 = new Topic("Information Security");
    Topic[] topics = {topic, topic1};
    generalNewsDao.addTopics(generalNews, Arrays.asList(topics));
    assertEquals(2, generalNewsDao.getTopics(generalNews.getId()).size());
  }

  @Test
  @DisplayName("Test that a general news post can be deleted")
  public void delete_deletesGeneralNewsPost_false(GeneralNews generalNews) {
    generalNewsDao.add(generalNews);
    generalNewsDao.delete(generalNews.getId());
    assertFalse(generalNewsDao.getAll().contains(generalNews));
  }

  @Test
  @DisplayName("Test that general news posts can be cleared in the database")
  public void deleteAll_deletesAllGeneralNewsPosts_true(GeneralNews generalNews) {
    generalNewsDao.add(generalNews);
    generalNewsDao.deleteAll();
    assertEquals(0, generalNewsDao.getAll().size());
  }

  @AfterEach
  public void tearDown() {
    generalNewsDao.deleteAll();
    userDao.deleteAll();
    topicDao.deleteAll();
  }

  @AfterAll
  static void afterAll() {
    connection.close();
  }
}