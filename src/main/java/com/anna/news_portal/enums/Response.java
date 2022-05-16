package com.anna.news_portal.enums;


public enum Response {
  OK(200),
  NOT_FOUND(404),
  CONFLICT(409),
  BAD_REQUEST(400),
  CREATED(201);


  private int statusCode;

  Response(int statusCode) {
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }
}
