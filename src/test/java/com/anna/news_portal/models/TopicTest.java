package com.anna.news_portal.models;

import com.anna.news_portal.parameter_resolvers.TopicParameterResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TopicParameterResolver.class)
class TopicTest {
  @Test
  @DisplayName("Test that Topic class instance is instantiated correctly")
  public void newTopic_instantiatesCorrectly_true(Topic topic) {
    assertNotNull(topic);
  }

  @Test
  @DisplayName("Test that Topic class instance instantiates with id of topic")
  public void newTopic_instantiatesWithId_true(Topic topic) {
    assertEquals(0, topic.getId());
  }

  @Test
  @DisplayName("Test that Topic class instance instantiates with name of topic")
  public void newTopic_instantiatesWithName_true(Topic topic) {
    assertEquals("Systems Security", topic.getName());
  }

  @Test
  @DisplayName("Test that a topic's id is set as specified")
  public void setName_setsTopicIdCorrectly_true(Topic topic) {
    topic.setId(1);
    assertEquals(1, topic.getId());
  }

  @Test
  @DisplayName("Test that a topic's name is set as specified")
  public void setName_setsTopicNameCorrectly_true(Topic topic) {
    topic.setName("Server Administration");
    assertEquals("Server Administration", topic.getName());
  }

  @Test
  @DisplayName("Test that Topic class instance are regarded as same if having same property values")
  public void equals_returnsTrueIfSameProperties_true(Topic topic) {
    Topic topic1 = new Topic("Systems Security");
    assertEquals(topic, topic1);
    assertEquals(topic.hashCode(), topic1.hashCode());
  }
}