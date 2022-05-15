package com.anna.news_portal.models;

import com.anna.news_portal.parameter_resolvers.GeneralNewsParameterResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GeneralNewsParameterResolver.class)
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
  @DisplayName("Test that GeneralNews class instances regarded as same if having same properties")
  public void equals_returnsTrueIfSame_true(GeneralNews generalNews) {
    GeneralNews generalNews1 = new GeneralNews("Change in organizational email policy", "Emails are required to have 2FA authentication set");
    assertEquals(generalNews, generalNews1);
    assertEquals(generalNews.hashCode(), generalNews1.hashCode());
  }
}