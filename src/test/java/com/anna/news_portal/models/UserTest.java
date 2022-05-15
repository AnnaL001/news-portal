package com.anna.news_portal.models;

import com.anna.news_portal.parameter_resolvers.UserParameterResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UserParameterResolver.class)
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
}