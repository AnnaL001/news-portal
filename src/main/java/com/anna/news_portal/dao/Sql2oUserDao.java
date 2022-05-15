package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oUserDao implements NewsPortalDao<User> {
  private final Sql2o sql2o;

  public Sql2oUserDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public void add(User data) {
    String insertQuery = "INSERT INTO users (name, position, role, department_id) VALUES (:name, :position, :role, :department_id)";
    try(Connection connection = sql2o.open()) {
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
  public List<User> getAll() {
    String selectQuery = "SELECT * FROM users ORDER BY name";
    List<User> users;

    try(Connection connection = sql2o.open()){
      users = connection.createQuery(selectQuery)
              .executeAndFetch(User.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      users = new ArrayList<>();
    }

    return users;
  }

  @Override
  public User get(int id) {
    String selectQuery = "SELECT * FROM users WHERE id = :id";
    User user;

    try(Connection connection = sql2o.open()){
      user = connection.createQuery(selectQuery)
              .addParameter("id", id)
              .executeAndFetchFirst(User.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      user = new User("", "", 0);
    }

    return user;
  }

  @Override
  public void update(User data) {
    String updateQuery = "UPDATE users SET (name, position, department_id) = (:name, :position, :department_id) WHERE id = :id";
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
    String deleteQuery = "DELETE FROM users WHERE id = :id";
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
    String deleteQuery = "DELETE FROM users";
    try(Connection connection = sql2o.open()){
      connection.createQuery(deleteQuery)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
