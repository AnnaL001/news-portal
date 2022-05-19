package com.anna.news_portal;

import com.anna.news_portal.dao.*;
import com.anna.news_portal.enums.Response;
import com.anna.news_portal.exceptions.ApiException;
import com.anna.news_portal.models.*;
import com.anna.news_portal.response.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.sql2o.Sql2o;


import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal", "anna", "pol1234");
    Sql2oDepartmentDao departmentDao = new Sql2oDepartmentDao(sql2o);
    Sql2oUserDao userDao = new Sql2oUserDao(sql2o);
    Sql2oGeneralNewsDao generalNewsDao = new Sql2oGeneralNewsDao(sql2o);
    Sql2oDepartmentNewsDao departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
    Sql2oAdminDao adminDao = new Sql2oAdminDao(sql2o);
    Sql2oTopicDao topicDao = new Sql2oTopicDao(sql2o);
    Gson gson = new Gson();

    // CREATE DEPARTMENT
    post("/departments", "application/json", (request, response) -> {
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

    // CREATE GENERAL NEWS BY USER
    post("/users/:id/news", "application/json", (request, response) -> {
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
          // Set owner of news post
          generalNews.setUser_id(user.getId());
          // Insert news post
          generalNewsDao.add(generalNews);
          return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "Success"));
      }
    });

    // CREATE DEPARTMENT NEWS BY USER
    post("/departments/:departmentId/user/:userId/news", "application/json", (request, response) -> {
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
        // Set owner of news post
        departmentNews.setUser_id(user.getId());
        // Set department associated with news post
        departmentNews.setDepartment_id(department.getId());
        // Insert department news post
        departmentNewsDao.add(departmentNews);
        return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "Success"));
      }
    });

    // CREATE GENERAL NEWS POST BY ADMIN
    post("/admins/:id/news", "application/json", (request, response) -> {
      Admin admin = adminDao.get(parseInt(request.params("id")));
      GeneralNews generalNews = gson.fromJson(request.body(), GeneralNews.class);

      // Set news type to general if not null
      if(generalNews != null){
        generalNews.setNews_type(GeneralNews.NEWS_TYPE);
      } else {
        throw new ApiException("No input provided", Response.BAD_REQUEST);
      }

      // if admin not found
      if(admin == null){
        throw new ApiException(String.format("No admin with the id: '%s' exists", request.params("id")), Response.NOT_FOUND);
      } else if (generalNewsDao.getAll().contains(generalNews)){
        // if duplicate data
        throw new ApiException("Duplicate record", Response.CONFLICT);
      } else {
        // Set owner of news
        generalNews.setUser_id(admin.getId());
        // Insert general news
        generalNewsDao.add(generalNews);
        return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "Success"));
      }
    });

    // CREATE DEPARTMENT NEWS POST BY ADMIN
    post("/departments/:departmentId/admins/:adminId/news", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("departmentId")));
      Admin admin = adminDao.get(parseInt(request.params("adminId")));
      DepartmentNews departmentNews = gson.fromJson(request.body(), DepartmentNews.class);

      // Set news type to departmental if not null
      if(departmentNews != null){
        departmentNews.setNews_type(DepartmentNews.NEWS_TYPE);
      } else {
        throw new ApiException("No input provided", Response.BAD_REQUEST);
      }

      if(department == null){
        throw new ApiException(String.format("No department with the id: '%s' exists", request.params("departmentId")), Response.NOT_FOUND);
      } else if (admin == null){
        throw new ApiException(String.format("No admin with the id: '%s' exists", request.params("adminId")), Response.NOT_FOUND);
      } else if (department.getId() != admin.getDepartment_id()){
        // If admin is not in department they'd like to post news too
        throw new ApiException("You can only post news in your department", Response.BAD_REQUEST);
      } else if (departmentNewsDao.getAll().contains(departmentNews)) {
        // If duplicate news
        throw new ApiException("Duplicate record", Response.CONFLICT);
      } else {
        // Set owner of news
        departmentNews.setUser_id(admin.getId());
        // Set intended department
        departmentNews.setDepartment_id(department.getId());
        // Insert department news
        departmentNewsDao.add(departmentNews);
        return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "Success"));
      }
    });

    // CREATE USER
    post("/departments/:id/users", "application/json", (request, response) -> {
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

    // CREATE ADMIN
    post("/departments/:id/admins", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("id")));
      Admin admin = gson.fromJson(request.body(), Admin.class);

      if(admin != null){
        admin.setRole(Admin.ROLE);
      } else {
        throw new ApiException("No input provided", Response.BAD_REQUEST);
      }

      if(department == null){
        throw new ApiException(String.format("No department with the id: '%s' exists", request.params("id")), Response.NOT_FOUND);
      } else if (adminDao.getAll().contains(admin)) {
        throw new ApiException("Duplicate record", Response.CONFLICT);
      } else {
        admin.setDepartment_id(department.getId());
        adminDao.add(admin);
        // Update department's list of users & admin and employee count
        department.setUsers(departmentDao.getUsers(department.getId()));
        department.setEmployee_count();
        departmentDao.update(department);
        return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "Success"));
      }
    });

    // READ USERS
    get("/users", "application/json", (request, response) -> {
      if(userDao.getAll().size() > 0){
        List<Map<String, Object>> users = userDao.transformUsers(userDao.getAll());
        ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(),"Success", new Gson().toJsonTree(users));
        return gson.toJson(apiResponse);
      } else {
        throw new ApiException("No users listed", Response.NOT_FOUND);
      }
    });

    // READ USER
    get("/users/:id", "application/json", (request, response) -> {
      User user = userDao.get(parseInt(request.params("id")));

      if(user != null){
        Map<String, Object> userMap = userDao.transform(user);
        ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), "Success", new Gson().toJsonTree(userMap));
        return gson.toJson(apiResponse);
      } else {
        throw new ApiException(String.format("No user with the id: '%s' exists", request.params("id")), Response.NOT_FOUND);
      }
    });

    // READ DEPARTMENTS
    get("/departments", "application/json", (request, response) -> {
      if(departmentDao.getAll().size() > 0){
        ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), "Success", new Gson().toJsonTree(departmentDao.getAll()));
        return gson.toJson(apiResponse);
      } else {
        throw new ApiException("No departments listed", Response.NOT_FOUND);
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
    get("/departments/:id/users", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("id")));

      if(department != null){
        // Retrieve users/employees within a department
        List<User> departmentUsers = departmentDao.getUsers(department.getId());
        if(departmentUsers.size() > 0){
          ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), "Success", new Gson().toJsonTree(userDao.transformUsers(departmentUsers)));
          return gson.toJson(apiResponse);
        } else {
          throw new ApiException(String.format("No users in department with the id: '%s' listed", request.params("id")), Response.NOT_FOUND);
        }
      } else {
        throw new ApiException(String.format("No department with the id: '%s' exists", request.params("id")), Response.NOT_FOUND);
      }
    });

    // READ DEPARTMENT NEWS
    get("/departments/:id/news", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("id")));

      if(department != null){
        List<DepartmentNews> departmentNews = departmentDao.getNews(department.getId());
        if(departmentNews.size() > 0){
          ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), "Success", new Gson().toJsonTree(departmentNewsDao.transformDepartmentNewsList(departmentNews)));
          return gson.toJson(apiResponse);
        } else {
          throw new ApiException(String.format("No news posts in department with the id: '%s' listed", request.params("id")), Response.NOT_FOUND);
        }
      } else {
        throw new ApiException(String.format("No department with the id: '%s' exists", request.params("id")), Response.NOT_FOUND);
      }
    });

    // READ GENERAL NEWS
    get("/news", "application/json", (request, response) -> {
      List<GeneralNews> generalNewsList = generalNewsDao.getAll();
      if(generalNewsList.size() > 0){
        ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), "Success", new Gson().toJsonTree(generalNewsDao.transformGeneralNewsList(generalNewsList)));
        return gson.toJson(apiResponse);
      } else {
        throw new ApiException("No general news listed", Response.NOT_FOUND);
      }
    });

    // ADD TOPICS TO GENERAL NEWS(USER)
    post("/users/:userId/news/:newsId/topics", "application/json", (request, response) -> {
      User user = userDao.get(parseInt(request.params("userId")));
      GeneralNews generalNews = generalNewsDao.get(parseInt(request.params("newsId")));
      List<Topic> newsTopics = gson.fromJson(request.body(), new TypeToken<List<Topic>>() {}.getType());

      if(user == null){
        throw new ApiException(String.format("No user with the id: '%s' exists", request.params("userId")), Response.NOT_FOUND);
      } else if (generalNews == null){
        throw new ApiException(String.format("No general news post with the id: '%s' exists", request.params("newsId")), Response.NOT_FOUND);
      } else if (generalNews.getUser_id() != user.getId()){
        throw new ApiException("You can only add topics to a post that you created", Response.BAD_REQUEST);
      } else if(newsTopics == null){
        throw new ApiException("No input provided", Response.BAD_REQUEST);
      } else {
        // Add news post's topics
        generalNewsDao.addTopics(generalNews, newsTopics);
        return gson.toJson(new ApiResponse(Response.OK.getStatusCode(), "Success"));
      }
    });

    // ADD TOPICS TO DEPARTMENT NEWS(USER)
    post("/departments/:departmentId/users/:userId/news/:newsId/topics/", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("departmentId")));
      User user = userDao.get(parseInt(request.params("userId")));
      DepartmentNews departmentNews = departmentNewsDao.get(parseInt(request.params("newsId")));
      List<Topic> newsTopics = gson.fromJson(request.body(), new TypeToken<List<Topic>>() {}.getType());

      if(department == null){
        throw new ApiException(String.format("No department with the id: '%s' exists", request.params("departmentId")), Response.NOT_FOUND);
      } else if(user == null){
        throw new ApiException(String.format("No user with the id: '%s' exists", request.params("userId")), Response.NOT_FOUND);
      } else if (departmentNews == null){
        throw new ApiException(String.format("No department news post with the id: '%s' exists", request.params("newsId")), Response.NOT_FOUND);
      } else if (departmentNews.getUser_id() != user.getId()){
        throw new ApiException("You can only add topics to a post that you created", Response.BAD_REQUEST);
      } else if (newsTopics == null){
        throw new ApiException("No input provided", Response.NOT_FOUND);
      } else {
        // Add news post's topics
        departmentNewsDao.addTopics(departmentNews, newsTopics);
        return gson.toJson(new ApiResponse(Response.OK.getStatusCode(), "Success"));
      }
    });

    // ADD TOPICS TO GENERAL NEWS(ADMIN)
    post("/admins/:adminId/news/:newsId/topics", "application/json", (request, response) -> {
      Admin admin = adminDao.get(parseInt(request.params("adminId")));
      GeneralNews generalNews = generalNewsDao.get(parseInt(request.params("newsId")));
      List<Topic> newsTopics = gson.fromJson(request.body(), new TypeToken<List<Topic>>() {}.getType());

      if(admin == null){
        throw new ApiException(String.format("No admin with the id: '%s' exists", request.params("adminId")), Response.NOT_FOUND);
      } else if (generalNews == null){
        throw new ApiException(String.format("No general news post with the id: '%s' exists", request.params("newsId")), Response.NOT_FOUND);
      } else if (generalNews.getUser_id() != admin.getId()){
        throw new ApiException("You can only add topics to a post that you created", Response.BAD_REQUEST);
      } else if(newsTopics == null){
        throw new ApiException("No input provided", Response.BAD_REQUEST);
      } else {
        generalNewsDao.addTopics(generalNews, newsTopics);
        return gson.toJson(new ApiResponse(Response.OK.getStatusCode(), "Success"));
      }
    });

    // ADD TOPICS TO DEPARTMENT NEWS(ADMIN)
    post("/departments/:departmentId/admin/:adminId/news/:newsId/topics/", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("departmentId")));
      Admin admin = adminDao.get(parseInt(request.params("adminId")));
      DepartmentNews departmentNews = departmentNewsDao.get(parseInt(request.params("newsId")));
      List<Topic> newsTopics = gson.fromJson(request.body(), new TypeToken<List<Topic>>() {}.getType());

      if(department == null){
        throw new ApiException(String.format("No department with the id: '%s' exists", request.params("departmentId")), Response.NOT_FOUND);
      } else if(admin == null){
        throw new ApiException(String.format("No admin with the id: '%s' exists", request.params("adminId")), Response.NOT_FOUND);
      } else if (departmentNews == null){
        throw new ApiException(String.format("No department news post with the id: '%s' exists", request.params("newsId")), Response.NOT_FOUND);
      } else if (departmentNews.getUser_id() != admin.getId()){
        throw new ApiException("You can only add topics to a post that you created", Response.BAD_REQUEST);
      } else if (newsTopics == null){
        throw new ApiException("No input provided", Response.NOT_FOUND);
      } else {
        departmentNewsDao.addTopics(departmentNews, newsTopics);
        return gson.toJson(new ApiResponse(Response.OK.getStatusCode(), "Success"));
      }
    });

    // GET NEWS TOPICS
    get("/topics", "application/json", (request, response) -> {
      if(topicDao.getAll().size() > 0){
        ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), "Success", new Gson().toJsonTree(topicDao.getAll()));
        return gson.toJson(apiResponse);
      } else {
        throw new ApiException("No topics listed", Response.NOT_FOUND);
      }
    });

    // DELETE GENERAL NEWS POSTS BY OWNER(USER)
    delete("/users/:userId/news/:newsId/delete", "application/json", (request, response) -> {
      User user = userDao.get(parseInt(request.params("userId")));
      GeneralNews generalNews = generalNewsDao.get(parseInt(request.params("newsId")));

      if(user == null){
        throw new ApiException(String.format("No user with the id: '%s' listed", request.params("userId")), Response.NOT_FOUND);
      } else if(generalNews == null){
        throw new ApiException(String.format("No general news post with the id: '%s' listed", request.params("newsId")), Response.NOT_FOUND);
      } else if(generalNews.getUser_id() != parseInt(request.params("userId"))){
        throw new ApiException("You can only delete the news post you created", Response.BAD_REQUEST);
      } else {
        generalNewsDao.delete(generalNews.getId());
        return gson.toJson(new ApiResponse(Response.OK.getStatusCode(), "Success"));
      }
    });

    // DELETE GENERAL NEWS POSTS BY OWNER(ADMIN)
    delete("/admins/:adminId/news/:newsId/delete", "application/json", (request, response) -> {
      Admin admin = adminDao.get(parseInt(request.params("adminId")));
      GeneralNews generalNews = generalNewsDao.get(parseInt(request.params("newsId")));

      if(admin == null){
        throw new ApiException(String.format("No admin with the id: '%s' listed", request.params("adminId")), Response.NOT_FOUND);
      } else if(generalNews == null){
        throw new ApiException(String.format("No general news post with the id: '%s' listed", request.params("newsId")), Response.NOT_FOUND);
      } else if(generalNews.getUser_id() != parseInt(request.params("adminId"))){
        throw new ApiException("You can only delete the news post you created", Response.BAD_REQUEST);
      } else {
        generalNewsDao.delete(generalNews.getId());
        return gson.toJson(new ApiResponse(Response.OK.getStatusCode(), "Success"));
      }
    });

    // DELETE DEPARTMENT NEWS POSTS BY OWNER(USER)
    delete("/departments/:departmentId/users/:userId/news/:newsId/delete", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("departmentId")));
      User user = userDao.get(parseInt(request.params("userId")));
      DepartmentNews departmentNews = departmentNewsDao.get(parseInt(request.params("newsId")));

      if(department == null){
        throw new ApiException(String.format("No department with the id: '%s' listed", request.params("departmentId")), Response.NOT_FOUND);
      } else if(user == null){
        throw new ApiException(String.format("No user with the id: '%s' listed", request.params("userId")), Response.NOT_FOUND);
      } else if (departmentNews == null){
        throw new ApiException(String.format("No department news post with the id: '%s' listed", request.params("newsId")), Response.NOT_FOUND);
      } else if (departmentNews.getUser_id() != user.getId()){
        throw new ApiException("You can only delete the news post that you created", Response.NOT_FOUND);
      } else {
        departmentNewsDao.delete(departmentNews.getId());
        return gson.toJson(new ApiResponse(Response.OK.getStatusCode(), "Success"));
      }
    });

    // DELETE DEPARTMENT NEWS POSTS BY OWNER(ADMIN)
    delete("/departments/:departmentId/admins/:adminId/news/:newsId/delete", "application/json", (request, response) -> {
      Department department = departmentDao.get(parseInt(request.params("departmentId")));
      Admin admin = adminDao.get(parseInt(request.params("adminId")));
      DepartmentNews departmentNews = departmentNewsDao.get(parseInt(request.params("newsId")));

      if(department == null){
        throw new ApiException(String.format("No department with the id: '%s' listed", request.params("departmentId")), Response.NOT_FOUND);
      } else if(admin == null){
        throw new ApiException(String.format("No admin with the id: '%s' listed", request.params("adminId")), Response.NOT_FOUND);
      } else if (departmentNews == null){
        throw new ApiException(String.format("No department news post with the id: '%s' listed", request.params("newsId")), Response.NOT_FOUND);
      } else if (departmentNews.getUser_id() != admin.getId()){
        throw new ApiException("You can only delete the news post that you created", Response.NOT_FOUND);
      } else {
        departmentNewsDao.delete(departmentNews.getId());
        return gson.toJson(new ApiResponse(Response.OK.getStatusCode(), "Success"));
      }
    });

    // DELETE USER BY ADMIN
    delete("/admins/:adminId/users/:userId/delete", "application/json", (request, response) -> {
      Admin admin = adminDao.get(parseInt(request.params("adminId")));
      User user = userDao.get(parseInt(request.params("userId")));

      if(admin == null){
        throw new ApiException(String.format("No admin with the id: '%s' listed", request.params("adminId")), Response.NOT_FOUND);
      } else if (user == null){
        throw new ApiException(String.format("No user with the id: '%s' listed", request.params("userId")), Response.NOT_FOUND);
      } else {
        // Check if a user is an admin before delete
        if(!Objects.equals(user.getRole(), Admin.ROLE)){
          userDao.delete(user.getId());
        } else {
          throw new ApiException("User is an admin", Response.BAD_REQUEST);
        }
        return gson.toJson(new ApiResponse(Response.OK.getStatusCode(), "Success"));
      }
    });

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