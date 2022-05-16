package com.anna.news_portal.models;

import com.anna.news_portal.base.News;


public class GeneralNews extends News {
  public static final String NEWS_TYPE = "General";

  public GeneralNews(String title, String content) {
    this.title = title;
    this.content = content;
    this.user_id = 0;
    setFormatted_created_date();
    this.formatted_created_date = getFormatted_created_date();
    this.news_type = NEWS_TYPE;
  }
}
