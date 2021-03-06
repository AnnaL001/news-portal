package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sql2oDepartmentNewsDao implements NewsPortalDao<DepartmentNews> {
  private final Sql2o sql2o;
  private static Sql2oTopicDao topicDao;
  private static Sql2oDepartmentDao departmentDao;
  private static Sql2oUserDao userDao;

  public Sql2oDepartmentNewsDao(Sql2o sql2o) {
    this.sql2o = sql2o;
    topicDao = new Sql2oTopicDao(sql2o);
    departmentDao = new Sql2oDepartmentDao(sql2o);
    userDao = new Sql2oUserDao(sql2o);
  }

  /**
   * Function to add department news post
   * @param data Department news post's data
   */
  @Override
  public void add(DepartmentNews data) {
    try(Connection connection = sql2o.open()){
      String insertQuery = "INSERT INTO news (title, content, user_id, news_type, department_id, created_at) VALUES (:title, :content, :user_id, :news_type, :department_id, now())";
      int id = (int) connection.createQuery(insertQuery, true)
              .bind(data)
              .executeUpdate()
              .getKey();
      data.setId(id);
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  /**
   * Function to retrieve list of department news posts
   * @return A list of department news posts
   */
  @Override
  public List<DepartmentNews> getAll() {
    String selectQuery = "SELECT * FROM news WHERE news_type = 'Departmental' ORDER BY created_at DESC";
    List<DepartmentNews> departmentNewsList;

    try(Connection connection = sql2o.open()) {
      departmentNewsList = connection.createQuery(selectQuery)
              .throwOnMappingFailure(false)
              .executeAndFetch(DepartmentNews.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      departmentNewsList = new ArrayList<>();
    }

    return departmentNewsList;
  }

  /**
   * Function to retrieve a specific department news post based on ID
   * @param id Department news post's id
   * @return Department news post's data
   */
  @Override
  public DepartmentNews get(int id) {
    String selectQuery = "SELECT * FROM news WHERE id = :id AND news_type = 'Departmental'";
    DepartmentNews departmentNews;

    try(Connection connection = sql2o.open()){
      departmentNews = connection.createQuery(selectQuery)
              .addParameter("id", id)
              .throwOnMappingFailure(false)
              .executeAndFetchFirst(DepartmentNews.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      departmentNews = new DepartmentNews("", "");
    }

    return departmentNews;
  }

  /**
   * Function to update department news post's data
   * @param data Department news post's updated data
   */
  @Override
  public void update(DepartmentNews data) {
    String updateQuery = "UPDATE news SET (title, content, user_id, department_id) = (:title, :content, :user_id, :department_id) WHERE id = :id";
    try(Connection connection = sql2o.open()){
      connection.createQuery(updateQuery)
              .bind(data)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  /**
   * Function to add topics to department news post
   * @param departmentNews Department news post
   * @param topics A list of topics
   */
  public void addTopics(DepartmentNews departmentNews, List<Topic> topics){
    String insertQuery = "INSERT INTO news_topics (news_id, topic_id) VALUES (:newsId, :topicId)";

    for(Topic topic: topics){
      // Add topic to database if it doesn't exist
      if(!topicDao.getAll().contains(topic)){
        topicDao.add(topic);
      }

      try (Connection connection = sql2o.open()){
        connection.createQuery(insertQuery)
                .addParameter("newsId", departmentNews.getId())
                .addParameter("topicId", topic.getId())
                .executeUpdate();
      } catch (Sql2oException exception){
        exception.printStackTrace();
      }
    }
  }

  /**
   * Function to retrieve a list of topics associated with department news post
   * @param newsId Department news post's id
   * @return A list of topics
   */
  public List<Topic> getTopics(int newsId){
    String selectQuery = "SELECT topics.* FROM news JOIN news_topics ON (news.id = news_topics.news_id) JOIN topics ON (news_topics.topic_id = topics.id) WHERE news.id = :newsId AND news.news_type = 'Departmental'";
    List<Topic> topicList;

    try(Connection connection = sql2o.open()) {
      topicList = connection.createQuery(selectQuery)
              .addParameter("newsId", newsId)
              .throwOnMappingFailure(false)
              .executeAndFetch(Topic.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      topicList = new ArrayList<>();
    }
    return topicList;
  }

  /**
   * Function to transform department news post's data for display to user
   * @param departmentNews Department news post's data
   * @return A transformed department news object
   */
  public Map<String, Object> transformDepartmentNews(DepartmentNews departmentNews){
    Department department = departmentDao.get(departmentNews.getDepartment_id());
    User user = userDao.get(departmentNews.getUser_id());
    List<Topic> topics = getTopics(departmentNews.getId());
    Map<String, Object> newsMap = new HashMap<>();
    newsMap.put("title", departmentNews.getTitle());
    newsMap.put("content", departmentNews.getContent());
    newsMap.put("topics", topics);
    newsMap.put("owner", userDao.transform(user));
    newsMap.put("department", department);
    newsMap.put("created_at", departmentNews.getCreated_at());
    newsMap.put("formatted_created_at", departmentNews.getFormatted_created_date());
    newsMap.put("news_type", departmentNews.getNews_type());
    return newsMap;
  }

  /**
   * Function to transform a list of department news posts for display to user
   * @param departmentNewsList A list of department news posts
   * @return Transformed list of department news posts
   */
  public List<Map<String, Object>> transformDepartmentNewsList(List<DepartmentNews> departmentNewsList){
    List<Map<String, Object>> newsMapList = new ArrayList<>();

    for(DepartmentNews departmentNews: departmentNewsList){
      newsMapList.add(transformDepartmentNews(departmentNews));
    }

    return newsMapList;
  }

  /**
   * Function to delete a department news post
   * @param id A department news post's id
   */
  @Override
  public void delete(int id) {
    String deleteQuery = "DELETE FROM news WHERE id = :id AND news_type = 'Departmental'";
    try(Connection connection = sql2o.open()){
      connection.createQuery(deleteQuery)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  /**
   * Function to delete all department news posts' data
   */
  @Override
  public void deleteAll() {
    String deleteQuery = "DELETE FROM news WHERE news_type = 'Departmental'";
    try(Connection connection = sql2o.open()){
      connection.createQuery(deleteQuery)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
