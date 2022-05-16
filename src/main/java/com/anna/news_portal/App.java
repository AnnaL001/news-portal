package com.anna.news_portal;

import com.anna.news_portal.dao.Sql2oDepartmentDao;
import com.anna.news_portal.dao.Sql2oDepartmentNewsDao;
import com.anna.news_portal.dao.Sql2oGeneralNewsDao;
import com.anna.news_portal.dao.Sql2oUserDao;
import com.anna.news_portal.enums.Response;
import com.anna.news_portal.exceptions.ApiException;
import com.anna.news_portal.models.Department;
import com.anna.news_portal.models.DepartmentNews;
import com.anna.news_portal.models.GeneralNews;
import com.anna.news_portal.models.User;
import com.anna.news_portal.response.ApiResponse;
import com.google.gson.Gson;
import org.sql2o.Sql2o;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal", "anna", "pol1234");
    Sql2oDepartmentDao departmentDao = new Sql2oDepartmentDao(sql2o);
    Sql2oUserDao userDao = new Sql2oUserDao(sql2o);
    Sql2oGeneralNewsDao generalNewsDao = new Sql2oGeneralNewsDao(sql2o);
    Sql2oDepartmentNewsDao departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
    Gson gson = new Gson();

    // CREATE DEPARTMENT
    post("/departments/new", "application/json", (request, response) -> {
      Department department = gson.fromJson(request.body(), Department.class);
      if(department == null){
        throw new ApiException("No input provided", Response.BAD_REQUEST);
      } else if(departmentDao.getAll().contains(department)){
        throw new ApiException("Duplicate record", Response.CONFLICT);
      } else {
        departmentDao.add(department);
        response.status(Response.CREATED.getStatusCode());
        return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "Success"));
      }
    });

    // CREATE GENERAL NEWS
    post("/users/:id/news/new", "application/json", (request, response) -> {
      User user = userDao.get(parseInt(request.params("id")));
      GeneralNews generalNews = gson.fromJson(request.body(), GeneralNews.class);

      // Set news type to general if not null
      if(generalNews != null){
        generalNews.setNews_type(GeneralNews.NEWS_TYPE);
      } else {
        throw new ApiException("No input provided", Response.BAD_REQUEST);
      }

      // if user not found
      if(user == null){
        throw new ApiException(String.format("No user with the id: '%s' exists", request.params("id")), Response.NOT_FOUND);
      } else if (generalNewsDao.getAll().contains(generalNews)){
          // if duplicate data
          throw new ApiException("Duplicate record", Response.CONFLICT);
      } else {
          generalNews.setUser_id(user.getId());
          generalNewsDao.add(generalNews);
          return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "Success"));
      }
    });

    // CREATE DEPARTMENT NEWS
    post("/departments/:departmentId/user/:userId/news/new", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("departmentId")));
      User user = userDao.get(parseInt(request.params("userId")));
      DepartmentNews departmentNews = gson.fromJson(request.body(), DepartmentNews.class);

      // Set news type to departmental if not null
      if(departmentNews != null){
        departmentNews.setNews_type(DepartmentNews.NEWS_TYPE);
      } else {
        throw new ApiException("No input provided", Response.BAD_REQUEST);
      }

      if(department == null){
        throw new ApiException(String.format("No department with the id: '%s' exists", request.params("departmentId")), Response.NOT_FOUND);
      } else if (user == null){
        throw new ApiException(String.format("No user with the id: '%s' exists", request.params("userId")), Response.NOT_FOUND);
      } else if (department.getId() != user.getDepartment_id()){
        throw new ApiException("You can only post news in your department", Response.BAD_REQUEST);
      } else if (departmentNewsDao.getAll().contains(departmentNews)) {
        throw new ApiException("Duplicate record", Response.CONFLICT);
      } else {
        departmentNews.setUser_id(user.getId());
        departmentNews.setDepartment_id(department.getId());
        departmentNewsDao.add(departmentNews);
        return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "Success"));
      }
    });

    // CREATE USER
    post("/departments/:id/users/new", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("id")));
      User user = gson.fromJson(request.body(), User.class);

      if(user != null){
        user.setRole(User.ROLE);
      } else {
        throw new ApiException("No input provided", Response.BAD_REQUEST);
      }

      if(department == null){
        throw new ApiException(String.format("No department with the id: '%s' exists", request.params("id")), Response.NOT_FOUND);
      } else if (userDao.getAll().contains(user)) {
        throw new ApiException("Duplicate record", Response.CONFLICT);
      } else {
        user.setDepartment_id(department.getId());
        userDao.add(user);
        // Update department's list of users and employee count
        department.setUsers(departmentDao.getUsers(department.getId()));
        department.setEmployee_count();
        departmentDao.update(department);
        return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "Success"));
      }
    });

    // READ USERS

    // READ USER

    // READ DEPARTMENTS
    get("/departments", "application/json", (request, response) -> {
      if(departmentDao.getAll().size() > 0){
        ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), "Success", new Gson().toJsonTree(departmentDao.getAll()));
        return gson.toJson(apiResponse);
      } else {
        throw new ApiException("No departments listed in the database", Response.NOT_FOUND);
      }
    });

    // READ DEPARTMENT
    get("/departments/:id", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("id")));

      if(department != null){
        ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), "Success", new Gson().toJsonTree(department));
        return gson.toJson(apiResponse);
      } else {
        throw new ApiException(String.format("No department with the id: '%s' exists", request.params("id")), Response.NOT_FOUND);
      }
    });

    // READ DEPARTMENT USERS

    // READ DEPARTMENT NEWS


    //FILTERS
    exception(ApiException.class, (exception, request, response) -> {
      ApiResponse apiResponse = new ApiResponse(exception.getStatus().getStatusCode(), exception.getMessage());
      response.type("application/json");
      response.status(exception.getStatus().getStatusCode());
      response.body(gson.toJson(apiResponse));
    });

    after((request, response) -> response.type("application/json"));
  }
}