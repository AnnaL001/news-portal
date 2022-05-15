CREATE DATABASE news_portal;
\c news_portal;

CREATE TABLE departments (
  id SERIAL PRIMARY KEY,
  name varchar UNIQUE,
  description varchar,
  employee_count int
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name varchar,
  position varchar,
  role varchar,
  department_id int
);

CREATE TABLE news (
  id SERIAL PRIMARY KEY,
  title varchar,
  content varchar,
  user_id int,
  news_type varchar,
  department_id int,
  created_at timestamp
);

CREATE TABLE topics (
  id SERIAL PRIMARY KEY,
  name varchar UNIQUE
);

CREATE TABLE news_topics (
  id SERIAL PRIMARY KEY,
  news_id int,
  topic_id int
);

CREATE DATABASE news_portal_test WITH TEMPLATE news_portal;