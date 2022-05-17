package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.Department;
import com.anna.news_portal.models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sql2oUserDao implements NewsPortalDao<User> {
  private final Sql2o sql2o;
  private static Sql2oDepartmentDao departmentDao;

  public Sql2oUserDao(Sql2o sql2o) {
    this.sql2o = sql2o;
    departmentDao = new Sql2oDepartmentDao(sql2o);
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
    String selectQuery = "SELECT * FROM users ORDER BY id";
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
      user = new User("", "");
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

  public Map<String, Object> transform(User user){
    Department department = departmentDao.get(user.getDepartment_id());
    Map<String, Object> userMap = new HashMap<>();
    userMap.put("id", user.getId());
    userMap.put("name", user.getName());
    userMap.put("position", user.getPosition());
    userMap.put("role", user.getRole());
    userMap.put("department", department);
    return userMap;
  }

  public List<Map<String, Object>> transformUsers(List<User> users){
    List<Map<String, Object>> transformedList = new ArrayList<>();
    for(User user: users){
      transformedList.add(transform(user));
    }
    return transformedList;
  }

  @Override
  public void delete(int id) {
    String deleteQuery = "DELETE FROM users WHERE id = :id AND role='Normal user'";
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
    String deleteQuery = "DELETE FROM users WHERE role = 'Normal user'";
    try(Connection connection = sql2o.open()){
      connection.createQuery(deleteQuery)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
