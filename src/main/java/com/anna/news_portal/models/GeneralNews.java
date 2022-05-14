package com.anna.news_portal.models;

import com.anna.news_portal.base.News;


public class GeneralNews extends News {
  public static final String NEWS_TYPE = "General";

  public GeneralNews(String title, String content) {
    this.title = title;
    this.content = content;
    this.news_type = NEWS_TYPE;
  }
}
