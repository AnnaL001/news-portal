package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.Topic;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oTopicDao implements NewsPortalDao<Topic> {
  private final Sql2o sql2o;

  public Sql2oTopicDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  /**
   * Function to add a topic
   * @param data A topic's data
   */
  @Override
  public void add(Topic data) {
    String insertQuery = "INSERT INTO topics (name) VALUES (:name)";
    try(Connection connection = sql2o.open()){
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
   * Function to retrieve list of topics
   * @return A list of topics
   */
  @Override
  public List<Topic> getAll() {
    String selectQuery = "SELECT * FROM topics ORDER BY id";
    List<Topic> topics;

    try(Connection connection = sql2o.open()){
      topics = connection.createQuery(selectQuery)
              .executeAndFetch(Topic.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      topics = new ArrayList<>();
    }

    return topics;
  }

  /**
   * Function to retrieve a specific topic based on ID
   * @param id A topic's id
   * @return A topic's data
   */
  @Override
  public Topic get(int id) {
    String selectQuery = "SELECT * FROM topics WHERE id = :id";
    Topic topic;

    try(Connection connection = sql2o.open()){
      topic = connection.createQuery(selectQuery)
              .addParameter("id", id)
              .executeAndFetchFirst(Topic.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      topic = new Topic("");
    }

    return topic;
  }

  /**
   * Function to update a topic's data
   * @param data A topic's updated data
   */
  @Override
  public void update(Topic data) {
    String updateQuery = "UPDATE topics SET name = :name WHERE id = :id";
    try(Connection connection = sql2o.open()){
      connection.createQuery(updateQuery)
              .bind(data)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  /**
   * Function to delete a topic
   * @param id A topic's id
   */
  @Override
  public void delete(int id) {
    String deleteQuery = "DELETE FROM topics WHERE id = :id";
    try(Connection connection = sql2o.open()){
      connection.createQuery(deleteQuery)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  /**
   * Function to delete all topics' data
   */
  @Override
  public void deleteAll() {
    String deleteQuery = "DELETE FROM topics";
    try(Connection connection = sql2o.open()){
      connection.createQuery(deleteQuery)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
