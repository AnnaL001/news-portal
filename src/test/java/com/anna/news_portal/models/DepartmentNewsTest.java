package com.anna.news_portal.models;

import com.anna.news_portal.parameter_resolvers.DepartmentNewsParameterResolver;
import com.anna.news_portal.parameter_resolvers.TopicParameterResolver;
import com.anna.news_portal.parameter_resolvers.UserParameterResolver;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DepartmentNewsParameterResolver.class)
@ExtendWith(UserParameterResolver.class)
@ExtendWith(TopicParameterResolver.class)
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
  @DisplayName("Test that DepartmentNews class instance instantiates with owner's information")
  public void newDepartmentNews_instantiatesWithOwnerData_true(DepartmentNews departmentNews) {
    assertEquals(1, departmentNews.getUser_id());
  }

  @Test
  @DisplayName("Test that DepartmentNews class instance instantiates with department information")
  public void newDepartmentNews_instantiatesWithDepartmentData_true(DepartmentNews departmentNews) {
    assertEquals(1, departmentNews.getDepartment_id());
  }

  @Test
  @DisplayName("Test that the department a news belongs to can be set as specified")
  public void setDepartment_id_setsDepartmentIdCorrectly_true(DepartmentNews departmentNews) {
    departmentNews.setDepartment_id(2);
    assertEquals(2, departmentNews.getDepartment_id());
  }

  @Test
  @DisplayName("Test that news id is set as specified")
  public void setId_setsIDCorrectly_true(DepartmentNews departmentNews) {
    departmentNews.setId(1);
    assertEquals(1, departmentNews.getId());
  }

  @Test
  @DisplayName("Test that news title is set as specified")
  public void setTitle_setsTitleCorrectly_true(DepartmentNews departmentNews) {
    departmentNews.setTitle("Departmental-wide meeting");
    assertEquals("Departmental-wide meeting", departmentNews.getTitle());
  }

  @Test
  @DisplayName("Test that news content is set as specified")
  public void setContent_setsContentCorrectly_true(DepartmentNews departmentNews) {
    departmentNews.setContent("Agenda of the meeting includes: ...");
    assertEquals("Agenda of the meeting includes: ...", departmentNews.getContent());
  }


  @Test
  @DisplayName("Test that news type is set as specified")
  public void setNews_type_setsNewsTypeCorrectly_true(DepartmentNews departmentNews) {
    departmentNews.setNews_type("Department news");
    assertEquals("Department news", departmentNews.getNews_type());
  }

  @Test
  @DisplayName("Test that the owner of a news post is set as specified")
  public void setUser_id_setsCorrectUserId_true(DepartmentNews departmentNews, User user) {
    user.setId(1);
    departmentNews.setUser_id(user.getId());
    assertEquals(1, departmentNews.getUser_id());
  }

  @Test
  @DisplayName("Test that news list of topics is set as specified")
  public void setTopics_setsNewsListOfTopics_true(DepartmentNews departmentNews, Topic topic) {
    Topic topic1 = new Topic("Two factor authentication");
    List<Topic> topics = new ArrayList<>(List.of(topic, topic1));
    departmentNews.setTopics(topics);
    assertEquals(topics, departmentNews.getTopics());
  }

  @Test
  @DisplayName("Test that time of creation is set as specified")
  public void setCreated_at_setsPostDateCorrectly_true(DepartmentNews departmentNews) {
    departmentNews.setCreated_at(new Timestamp(new Date().getTime()));
    assertEquals(new Timestamp(new Date().getTime()), departmentNews.getCreated_at());
    departmentNews.setFormatted_created_date();
    DateTimeZone zone = DateTimeZone.forID("Africa/Nairobi");
    LocalDateTime localDateTime = new LocalDateTime(departmentNews.getCreated_at(), zone);
    assertEquals(localDateTime, departmentNews.getFormatted_created_date());
  }

  @Test
  @DisplayName("Test that DepartmentNews class instances regarded as same if having same properties")
  public void equals_returnsTrueIfSame_true(DepartmentNews departmentNews) {
    DepartmentNews departmentNews1 = new DepartmentNews("Email 2FA authentication","The 2FA authentication adds an extra layer of security thus ensure security of accounts",1, 1);
    assertEquals(departmentNews, departmentNews1);
    assertEquals(departmentNews.hashCode(), departmentNews1.hashCode());
  }

}