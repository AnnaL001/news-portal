package com.anna.news_portal.models;

public class Topic {
  private int id;
  private String name;

  public Topic(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
