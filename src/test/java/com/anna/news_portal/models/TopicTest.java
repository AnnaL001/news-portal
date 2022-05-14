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
  @DisplayName("Test that Topic class instance instantiates with name of topic")
  public void newTopic_instantiatesWithName_true(Topic topic) {
    assertEquals("Systems Security", topic.getName());
  }

  @Test
  @DisplayName("Test that a topic's name is set as specified")
  public void setName_setsTopicNameCorrectly_true(Topic topic) {
    topic.setName("Server Administration");
    assertEquals("Server Administration", topic.getName());
  }
}