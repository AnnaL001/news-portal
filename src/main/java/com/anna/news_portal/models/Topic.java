package com.anna.news_portal.models;

import java.util.Objects;

public class Topic {
  private int id;
  private String name;

  public Topic(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Topic topic = (Topic) o;
    return Objects.equals(name, topic.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
