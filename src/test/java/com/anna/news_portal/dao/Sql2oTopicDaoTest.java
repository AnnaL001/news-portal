package com.anna.news_portal.dao;

import com.anna.news_portal.models.Topic;
import com.anna.news_portal.parameter_resolvers.TopicParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TopicParameterResolver.class)
class Sql2oTopicDaoTest {
  private static Connection connection;
  private static Sql2oTopicDao topicDao;

  @BeforeAll
  static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", "anna", "pol1234");
    topicDao = new Sql2oTopicDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a news topic can be added")
  public void add_addsNewsTopic_true(Topic topic) {
    topicDao.add(topic);
    assertTrue(topicDao.getAll().contains(topic));
  }

  @Test
  @DisplayName("Test that a news topic's id is set to a database generated id")
  public void add_setsNewsTopicId_true(Topic topic) {
    int initialId = topic.getId();
    topicDao.add(topic);
    assertNotEquals(initialId, topic.getId());
  }

  @Test
  @DisplayName("Test that a list of added topics can be retrieved")
  public void getAll_returnsTopicsList_true(Topic topic) {
    topicDao.add(topic);
    Topic[] topics = {topic};
    assertEquals(Arrays.asList(topics), topicDao.getAll());
  }

  @Test
  @DisplayName("Test that an empty list is returned if no topics are listed in the database")
  public void getAll_returnsEmptyListIfNoTopics_true() {
    assertEquals(0, topicDao.getAll().size());
  }

  @Test
  @DisplayName("Test that a topic can be retrieved")
  public void get_returnsTopic_true(Topic topic) {
    topicDao.add(topic);
    Topic foundTopic = topicDao.get(topic.getId());
    assertEquals(topic, foundTopic);
  }

  @Test
  @DisplayName("Test that a topic's data can be updated")
  public void update_updatesTopicData_true(Topic topic) {
    topicDao.add(topic);
    topic.setName("Information Security");
    topicDao.update(topic);

    // Retrieve updated topic
    Topic updatedTopic = topicDao.get(topic.getId());
    assertEquals(topic, updatedTopic);
  }

  @Test
  @DisplayName("Test that a topic can be deleted")
  public void delete_deletesATopic_true(Topic topic) {
    topicDao.add(topic);
    topicDao.delete(topic.getId());
    assertFalse(topicDao.getAll().contains(topic));
  }

  @Test
  @DisplayName("Test that all topics listed can be deleted")
  public void deleteAll_deletesAllTopics_true(Topic topic) {
    topicDao.add(topic);
    topicDao.deleteAll();
    assertEquals(0, topicDao.getAll().size());
  }

  @AfterEach
  public void tearDown() {
    topicDao.deleteAll();
  }

  @AfterAll
  static void afterAll() {
    connection.close();
  }
}