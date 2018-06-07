SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS artists (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    imageUrl VARCHAR
);

CREATE TABLE IF NOT EXISTS albums (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  releaseDate VARCHAR,
  tracks VARCHAR,
  imageUrl VARCHAR,
  artistId int
);

CREATE TABLE IF NOT EXISTS reviews (
  id int PRIMARY KEY auto_increment,
  rating int,
  author VARCHAR,
  comment VARCHAR,
  albumId int
);