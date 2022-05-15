package com.anna.news_portal.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

class Sql2oTopicDaoTest {
  private static Connection connection;
  private static Sql2oTopicDao topicDao;

  @BeforeAll
  static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", "anna", "pol1234");
    topicDao = new Sql2oTopicDao(sql2o);
    connection = sql2o.open();
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