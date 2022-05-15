package com.anna.news_portal.dao;

import com.anna.news_portal.interfaces.NewsPortalDao;
import com.anna.news_portal.models.Department;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentDao implements NewsPortalDao<Department> {
  private final Sql2o sql2o;

  public Sql2oDepartmentDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public void add(Department data) {
    try(Connection connection = sql2o.open()){
      String insertQuery = "INSERT INTO departments (name, description, employee_count) VALUES (:name, :description, :employeeCount)";
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
    String selectQuery = "SELECT * FROM departments ORDER BY name";
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

  }

  @Override
  public void delete(int id) {

  }

  @Override
  public void deleteAll() {

  }
}
