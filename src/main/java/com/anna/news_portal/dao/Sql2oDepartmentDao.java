package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.Department;
import com.anna.news_portal.models.DepartmentNews;
import com.anna.news_portal.models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sql2oDepartmentDao implements NewsPortalDao<Department> {
  private final Sql2o sql2o;

  public Sql2oDepartmentDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public void add(Department data) {
    try(Connection connection = sql2o.open()){
      String insertQuery = "INSERT INTO departments (name, description, employee_count) VALUES (:name, :description, :employee_count)";
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
  public List<Department> getAll() {
    String selectQuery = "SELECT * FROM departments ORDER BY id";
    List<Department> departments;

    try(Connection connection = sql2o.open()) {
      departments = connection.createQuery(selectQuery)
              .executeAndFetch(Department.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      departments = new ArrayList<>();
    }

    return departments;
  }

  @Override
  public Department get(int id) {
    String selectQuery = "SELECT * FROM departments WHERE id = :id";
    Department department;

    try(Connection connection = sql2o.open()){
      department = connection.createQuery(selectQuery)
              .addParameter("id", id)
              .executeAndFetchFirst(Department.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      department = new Department("", "");
    }

    return department;
  }

  @Override
  public void update(Department data) {
    String updateQuery = "UPDATE departments SET (name, description, employee_count) = (:name, :description, :employee_count) WHERE id = :id";
    try(Connection connection = sql2o.open()){
      connection.createQuery(updateQuery)
              .bind(data)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  public List<User> getUsers(int departmentId){
    Department department = get(departmentId);
    List<User> users;

    String selectQuery = "SELECT * FROM users WHERE department_id = :departmentId";
    try(Connection connection = sql2o.open()){
      users = connection.createQuery(selectQuery)
              .addParameter("departmentId", department.getId())
              .executeAndFetch(User.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      users = new ArrayList<>();
    }

    return users;
  }


  public List<DepartmentNews> getNews(int departmentId){
    Department department = get(departmentId);
    List<DepartmentNews> departmentNewsList;

    String selectQuery = "SELECT * FROM news WHERE department_id = :departmentId AND news_type = 'Departmental'";
    try(Connection connection = sql2o.open()){
      departmentNewsList = connection.createQuery(selectQuery)
              .addParameter("departmentId", department.getId())
              .throwOnMappingFailure(false)
              .executeAndFetch(DepartmentNews.class);
    } catch (Sql2oException exception){
      exception.printStackTrace();
      departmentNewsList = new ArrayList<>();
    }

    return departmentNewsList;
  }

  @Override
  public void delete(int id) {
    String deleteQuery = "DELETE FROM departments WHERE id = :id";
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
    String deleteQuery = "DELETE FROM departments";
    try(Connection connection = sql2o.open()){
      connection.createQuery(deleteQuery)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
