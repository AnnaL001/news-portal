package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.Admin;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oAdminDao implements NewsPortalDao<Admin> {
  private final Sql2o sql2o;

  public Sql2oAdminDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }


  @Override
  public void add(Admin data) {
    String insertQuery = "INSERT INTO users (name, position, role, department_id) VALUES (:name, :position, :role, :department_id)";
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
  public List<Admin> getAll() {
    String selectQuery = "SELECT * FROM users WHERE role = 'Admin'";
    List<Admin> admins;

    try(Connection connection = sql2o.open()) {
      admins = connection.createQuery(selectQuery)
              .executeAndFetch(Admin.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      admins = new ArrayList<>();
    }

    return admins;
  }

  @Override
  public Admin get(int id) {
    String selectQuery = "SELECT * FROM users WHERE id = :id AND role = 'Admin'";
    Admin admin;

    try(Connection connection = sql2o.open()) {
      admin = connection.createQuery(selectQuery)
              .addParameter("id", id)
              .executeAndFetchFirst(Admin.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      admin = new Admin("", "", 0);
    }

    return admin;
  }

  @Override
  public void update(Admin data) {
    String updateQuery = "UPDATE users SET (name, position, department_id) = (:name, :position, :department_id) WHERE id = :id AND role = 'Admin'";
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
    String deleteQuery = "DELETE FROM users WHERE id = :id AND role = 'Admin'";
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
    String deleteQuery = "DELETE FROM users WHERE role = 'Admin'";
    try(Connection connection = sql2o.open()){
      connection.createQuery(deleteQuery)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
