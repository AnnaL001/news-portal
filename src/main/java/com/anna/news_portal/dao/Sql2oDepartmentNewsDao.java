package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.DepartmentNews;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentNewsDao implements NewsPortalDao<DepartmentNews> {
  private final Sql2o sql2o;

  public Sql2oDepartmentNewsDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

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
      departmentNews = new DepartmentNews("", "", 0, 0);
    }

    return departmentNews;
  }

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
