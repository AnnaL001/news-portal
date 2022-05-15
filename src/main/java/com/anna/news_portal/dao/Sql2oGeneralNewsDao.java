package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.GeneralNews;
import com.anna.news_portal.models.Topic;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oGeneralNewsDao implements NewsPortalDao<GeneralNews> {
  private final Sql2o sql2o;
  private static Sql2oTopicDao topicDao;

  public Sql2oGeneralNewsDao(Sql2o sql2o) {
    this.sql2o = sql2o;
    topicDao = new Sql2oTopicDao(sql2o);
  }

  @Override
  public void add(GeneralNews data) {
    try(Connection connection = sql2o.open()){
      String insertQuery = "INSERT INTO news (title, content, user_id, news_type, created_at) VALUES (:title, :content, :user_id, :news_type, now())";
      int id = (int) connection.createQuery(insertQuery, true)
              .bind(data)
              .executeUpdate()
              .getKey();
      data.setId(id);
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public List<GeneralNews> getAll() {
    String selectQuery = "SELECT * FROM news WHERE news_type = 'General' ORDER BY created_at DESC";
    List<GeneralNews> generalNewsList;

    try(Connection connection = sql2o.open()) {
      generalNewsList = connection.createQuery(selectQuery)
              .throwOnMappingFailure(false)
              .executeAndFetch(GeneralNews.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      generalNewsList = new ArrayList<>();
    }

    return generalNewsList;
  }

  @Override
  public GeneralNews get(int id) {
    String selectQuery = "SELECT * FROM news WHERE id = :id AND news_type = 'General'";
    GeneralNews generalNews;

    try(Connection connection = sql2o.open()){
      generalNews = connection.createQuery(selectQuery)
              .addParameter("id", id)
              .throwOnMappingFailure(false)
              .executeAndFetchFirst(GeneralNews.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      generalNews = new GeneralNews("", "", 0);
    }

    return generalNews;
  }

  @Override
  public void update(GeneralNews data) {
    String updateQuery = "UPDATE news SET (title, content, user_id) = (:title, :content, :user_id) WHERE id = :id";
    try(Connection connection = sql2o.open()){
      connection.createQuery(updateQuery)
              .bind(data)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  public void addNewsTopics(GeneralNews generalNews, List<Topic> topics){
    String insertQuery = "INSERT INTO news_topics (news_id, topic_id) VALUES (:newsId, :topicId)";

    for(Topic topic: topics){
      // Add topic to database if it doesn't exist
      if(!topicDao.getAll().contains(topic)){
        topicDao.add(topic);
      }

      try (Connection connection = sql2o.open()){
        connection.createQuery(insertQuery)
                .addParameter("newsId", generalNews.getId())
                .addParameter("topicId", topic.getId())
                .executeUpdate();
      } catch (Sql2oException exception){
        exception.printStackTrace();
      }
    }
  }

  public List<Topic> getTopics(int newsId){
    String selectQuery = "SELECT topics.* FROM news JOIN news_topics ON (news.id = news_topics.news_id) JOIN topics ON (news_topics.topic_id = topic.id) WHERE news.id = :newsId AND news.news_type = 'General'";
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

  @Override
  public void delete(int id) {
    String deleteQuery = "DELETE FROM news WHERE id = :id AND news_type = 'General'";
    try(Connection connection = sql2o.open()){
      connection.createQuery(deleteQuery)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public void deleteAll() {
    String deleteQuery = "DELETE FROM news WHERE news_type = 'General'";
    try(Connection connection = sql2o.open()){
      connection.createQuery(deleteQuery)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
