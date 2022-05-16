package com.anna.news_portal.models;

import com.anna.news_portal.base.News;
import com.anna.news_portal.parameter_resolvers.DepartmentParameterResolver;
import com.anna.news_portal.parameter_resolvers.GeneralNewsParameterResolver;
import com.anna.news_portal.parameter_resolvers.UserParameterResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UserParameterResolver.class)
@ExtendWith(DepartmentParameterResolver.class)
@ExtendWith(GeneralNewsParameterResolver.class)
class UserTest {
  @Test
  @DisplayName("Test that a User class instance instantiates correctly")
  public void newUser_instantiatesCorrectly_true(User user) {
    assertNotNull(user);
  }

  @Test
  @DisplayName("Test that a User class instance instantiates with a name")
  public void newUser_instantiatesWithName_true(User user) {
    assertEquals("Jane Doe", user.getName());
  }

  @Test
  @DisplayName("Test that a User class instance instantiates with a position")
  public void newUser_instantiatesWithPosition_true(User user) {
    assertEquals("Chief Financial Officer", user.getPosition());
  }

  @Test
  @DisplayName("Test that a User class instance instantiates with role")
  public void newUser_instantiatesWithRole_true(User user) {
    assertEquals("Normal user", user.getRole());
  }

  @Test
  @DisplayName("Test that a User class instance instantiates with a default ID")
  public void newUser_instantiatesWithDefaultID_true(User user) {
    assertEquals(0, user.getId());
  }

  @Test
  @DisplayName("Test that a User class instance instantiates with a default department ID")
  public void newUser_instantiatesWithDefaultDepartmentID(User user) {
    assertEquals(0, user.getDepartment_id());
  }

  @Test
  @DisplayName("Test that a User class instance instantiates with an empty list of news posts posted")
  public void newUser_instantiatesWithEmptyPostsListID(User user) {
    assertEquals(0, user.getNews().size());
  }

  @Test
  @DisplayName("Test that a user's id is set as specified")
  public void setId_setsIDCorrectly_true(User user) {
    user.setId(1);
    assertEquals(1, user.getId());
  }

  @Test
  @DisplayName("Test that a user's name is set as specified")
  public void setName_setsNameCorrectly_true(User user) {
    user.setName("Logan Doe");
    assertEquals("Logan Doe", user.getName());
  }

  @Test
  @DisplayName("Test that a user's position is set as specified")
  public void setPosition_setsPositionCorrectly_true(User user) {
    user.setPosition("Marketing Director");
    assertEquals("Marketing Director", user.getPosition());
  }

  @Test
  @DisplayName("Test that a user's role is set as specified")
  public void setRole_setsRoleCorrectly_true(User user) {
    user.setRole("Normal users");
    assertEquals("Normal users", user.getRole());
  }

  @Test
  @DisplayName("Test that a user's department is set as specified")
  public void setDepartment_id_setsDepartmentCorrectly_true(User user, Department department) {
    department.setId(1);
    user.setDepartment_id(department.getId());
    assertEquals(1, user.getDepartment_id());
  }

  @Test
  @DisplayName("Test that a user's news posts list is set as specified")
  void setDepartment_id_setsPostsListCorrectly_true(User user, GeneralNews generalNews) {
    generalNews.setId(1);
    List<News> generalNewsList = new ArrayList<>(List.of(generalNews));
    user.setNews(generalNewsList);
    assertEquals(1, user.getNews().size());
  }

  @Test
  @DisplayName("Test that User class instances are regarded as same if having same property values")
  public void equals_returnsSameIfSameProperties(User user) {
    User user1 = new User("Jane Doe", "Chief Financial Officer");
    assertEquals(user, user1);
    assertEquals(user.hashCode(), user1.hashCode());
  }
}