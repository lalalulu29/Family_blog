
DROP TABLE if EXISTS posts;
DROP TABLE if EXISTS users;
DROP TABLE if EXISTS comments;


CREATE TABLE if not exist users (
  id BIGSERIAL PRIMARY KEY,
  name varchar(128),
  login varchar(128),
  password varchar(256),
  created_at timestamp not null default current_timestamp,
  age int
);


CREATE TABLE if not exist posts (
  id BIGSERIAL PRIMARY KEY,
  author_id bigint,
  created_at timestamp not null default current_timestamp,
  title VARCHAR(1024),
  text text
);



CREATE TABLE if not exist comments (
  id BIGSERIAL PRIMARY KEY,
  post_id bigint,
  author_id bigint,
  text text,
  created_ad timestamp not null default current_timestamp
);