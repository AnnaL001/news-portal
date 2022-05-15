package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.GeneralNews;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oGeneralNewsDao implements NewsPortalDao<GeneralNews> {
  private final Sql2o sql2o;

  public Sql2oGeneralNewsDao(Sql2o sql2o) {
    this.sql2o = sql2o;
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
