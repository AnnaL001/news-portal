package com.anna.news_portal.models;

import com.anna.news_portal.parameter_resolvers.DepartmentNewsParameterResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DepartmentNewsParameterResolver.class)
class DepartmentNewsTest {
  @Test
  @DisplayName("Test that DepartmentNews class instance instantiates correctly")
  public void newDepartmentNews_instantiatesCorrectly_true(DepartmentNews departmentNews) {
    assertNotNull(departmentNews);
  }

  @Test
  @DisplayName("Test that DepartmentNews class instance instantiates with title")
  public void newDepartmentNews_instantiatesWithTitle_true(DepartmentNews departmentNews) {
    assertEquals("Email 2FA authentication", departmentNews.getTitle());
  }

  @Test
  @DisplayName("Test that DepartmentNews class instance instantiates with content")
  public void newDepartmentNews_instantiatesWithContent_true(DepartmentNews departmentNews) {
    assertEquals("The 2FA authentication adds an extra layer of security thus ensure security of accounts", departmentNews.getContent());
  }

  @Test
  @DisplayName("Test that DepartmentNews class instance instantiates with news type")
  public void newDepartmentNews_instantiatesWithNewsType_true(DepartmentNews departmentNews) {
    assertEquals(DepartmentNews.NEWS_TYPE, departmentNews.getNews_type());
  }

  @Test
  @DisplayName("Test that the department a news belongs to can be retrieved")
  public void getDepartment_id_returnsCorrectDepartmentId_true(DepartmentNews departmentNews) {
    assertEquals(0, departmentNews.getDepartment_id());
  }

  @Test
  @DisplayName("Test that the department a news belongs to can be set as specified")
  public void setDepartment_id_setsDepartmentIdCorrectly_true(DepartmentNews departmentNews) {
    departmentNews.setDepartment_id(1);
    assertEquals(1, departmentNews.getDepartment_id());
  }
}