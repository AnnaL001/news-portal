package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.Topic;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oTopicsDao implements NewsPortalDao<Topic> {
  private final Sql2o sql2o;

  public Sql2oTopicsDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

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

  @Override
  public List<Topic> getAll() {
    String selectQuery = "SELECT * FROM topics ORDER BY name";
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

  @Override
  public void update(Topic data) {

  }

  @Override
  public void delete(int id) {

  }

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
