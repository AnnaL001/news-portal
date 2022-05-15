package com.anna.news_portal.base;

import com.anna.news_portal.models.Topic;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.sql.Timestamp;

import java.util.List;
import java.util.Objects;

public abstract class News {
  protected int id;
  protected String title;
  protected String content;
  protected int user_id;

  protected String news_type;
  protected List<Topic> topics;
  protected Timestamp created_at;
  protected LocalDateTime formatted_created_date;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getNews_type() {
    return news_type;
  }

  public void setNews_type(String news_type) {
    this.news_type = news_type;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public List<Topic> getTopics() {
    return topics;
  }

  public void setTopics(List<Topic> topics) {
    this.topics = topics;
  }

  public Timestamp getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Timestamp created_at) {
    this.created_at = created_at;
  }

  public LocalDateTime getFormatted_created_date() {
    return formatted_created_date;
  }

  public void setFormatted_created_date() {
    DateTimeZone zone = DateTimeZone.forID("Africa/Nairobi");
    this.formatted_created_date = new LocalDateTime(this.created_at, zone);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    News news = (News) o;
    return user_id == news.user_id && Objects.equals(title, news.title) && Objects.equals(content, news.content) && Objects.equals(news_type, news.news_type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, content, user_id, news_type);
  }
}
