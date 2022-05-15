package com.anna.news_portal.models;

import com.anna.news_portal.base.News;

public class DepartmentNews extends News {
  private int department_id;
  public static final String NEWS_TYPE = "Departmental";

  public DepartmentNews(String title, String content, int user_id, int department_id) {
    this.title = title;
    this.content = content;
    this.user_id = user_id;
    this.department_id = department_id;
    setFormatted_created_date();
    this.formatted_created_date = getFormatted_created_date();
    this.news_type = NEWS_TYPE;
  }

  public int getDepartment_id() {
    return department_id;
  }

  public void setDepartment_id(int department_id) {
    this.department_id = department_id;
  }
}
