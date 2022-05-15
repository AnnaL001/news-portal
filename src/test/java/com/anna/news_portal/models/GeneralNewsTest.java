package com.anna.news_portal.models;

import com.anna.news_portal.parameter_resolvers.GeneralNewsParameterResolver;
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

@ExtendWith(GeneralNewsParameterResolver.class)
@ExtendWith(UserParameterResolver.class)
@ExtendWith(TopicParameterResolver.class)
class GeneralNewsTest {
  @Test
  @DisplayName("Test that GeneralNews class instance instantiates correctly")
  public void newGeneralNews_instantiatesCorrectly_true(GeneralNews generalNews) {
    assertNotNull(generalNews);
  }

  @Test
  @DisplayName("Test that GeneralNews class instance instantiates with title")
  public void newGeneralNews_instantiatesWithTitle_true(GeneralNews generalNews) {
    assertEquals("Change in organizational email policy", generalNews.getTitle());
  }

  @Test
  @DisplayName("Test that GeneralNews class instance instantiates with content")
  public void newGeneralNews_instantiatesWithContent_true(GeneralNews generalNews) {
    assertEquals("Emails are required to have 2FA authentication set", generalNews.getContent());
  }

  @Test
  @DisplayName("Test that GeneralNews class instance instantiates with news type")
  public void newGeneralNews_instantiatesWithNewsType_true(GeneralNews generalNews) {
    assertEquals(GeneralNews.NEWS_TYPE, generalNews.getNews_type());
  }

  @Test
  @DisplayName("Test that GeneralNews class instance instantiates with owner's id")
  public void newGeneralNews_instantiatesWithOwnerData_true(GeneralNews generalNews) {
    assertEquals(1, generalNews.getUser_id());
  }

  @Test
  @DisplayName("Test that news id is set as specified")
  public void setId_setsIDCorrectly_true(GeneralNews generalNews) {
    generalNews.setId(1);
    assertEquals(1, generalNews.getId());
  }

  @Test
  @DisplayName("Test that news title is set as specified")
  public void setTitle_setsTitleCorrectly_true(GeneralNews generalNews) {
    generalNews.setTitle("Company-wide meeting");
    assertEquals("Company-wide meeting", generalNews.getTitle());
  }

  @Test
  @DisplayName("Test that news content is set as specified")
  public void setContent_setsContentCorrectly_true(GeneralNews generalNews) {
    generalNews.setContent("Agenda of the meeting includes: ...");
    assertEquals("Agenda of the meeting includes: ...", generalNews.getContent());
  }


  @Test
  @DisplayName("Test that news type is set as specified")
  public void setNews_type_setsNewsTypeCorrectly_true(GeneralNews generalNews) {
    generalNews.setNews_type("Company-wide news");
    assertEquals("Company-wide news", generalNews.getNews_type());
  }

  @Test
  @DisplayName("Test that the owner of a news post is set as specified")
  public void setUser_id_setsCorrectUserId_true(GeneralNews generalNews, User user) {
    user.setId(1);
    generalNews.setUser_id(user.getId());
    assertEquals(1, generalNews.getUser_id());
  }

  @Test
  @DisplayName("Test that news list of topics is set as specified")
  public void setTopics_setsNewsListOfTopics_true(GeneralNews generalNews, Topic topic) {
    Topic topic1 = new Topic("Two factor authentication");
    List<Topic> topics = new ArrayList<>(List.of(topic, topic1));
    generalNews.setTopics(topics);
    assertEquals(topics, generalNews.getTopics());
  }

  @Test
  @DisplayName("Test that time of creation is set as specified")
  public void setCreated_at_setsPostDateCorrectly_true(GeneralNews generalNews) {
    generalNews.setCreated_at(new Timestamp(new Date().getTime()));
    assertEquals(new Timestamp(new Date().getTime()), generalNews.getCreated_at());
    generalNews.setFormatted_created_date();
    DateTimeZone zone = DateTimeZone.forID("Africa/Nairobi");
    LocalDateTime localDateTime = new LocalDateTime(generalNews.getCreated_at(), zone);
    assertEquals(localDateTime, generalNews.getFormatted_created_date());
  }


  @Test
  @DisplayName("Test that GeneralNews class instances regarded as same if having same properties")
  public void equals_returnsTrueIfSame_true(GeneralNews generalNews) {
    GeneralNews generalNews1 = new GeneralNews("Change in organizational email policy", "Emails are required to have 2FA authentication set", 1);
    assertEquals(generalNews, generalNews1);
    assertEquals(generalNews.hashCode(), generalNews1.hashCode());
  }
}