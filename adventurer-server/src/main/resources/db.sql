DROP DATABASE treasure_adventurer;
CREATE DATABASE treasure_adventurer;
ALTER DATABASE treasure_adventurer CHARACTER SET utf8 COLLATE utf8_general_ci;
use treasure_adventurer;

create table country (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(128),
  phoneCode VARCHAR(10)
);
ALTER TABLE country CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table province (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  country_id INT,
  title VARCHAR(128),
  CONSTRAINT fk_province_country FOREIGN KEY (country_id) REFERENCES country (id)
);
ALTER TABLE province CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table city (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  country_id INT,
  province_id INT,
  title VARCHAR(128),
  phoneCode VARCHAR(10),
  CONSTRAINT fk_city_country FOREIGN KEY (country_id) REFERENCES country (id),
  CONSTRAINT fk_city_province FOREIGN KEY (province_id) REFERENCES province (id)
);
ALTER TABLE city CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table user (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(128),
  gmail VARCHAR(128),
  mobile VARCHAR(11),
  password VARCHAR(128),
  image VARCHAR(128),
  enter_date timestamp NOT NULL,
  CONSTRAINT uk_user_gmail UNIQUE KEY(gmail),
  CONSTRAINT uk_user_mobile UNIQUE KEY(mobile)
);
ALTER TABLE user CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table user_info (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL UNIQUE,
  first_name VARCHAR(40),
  last_name VARCHAR(40),
  full_name VARCHAR(80),
  national_code VARCHAR(10),
  birthdate CHAR(10),
  age INT,
  CONSTRAINT fk_user_info_user FOREIGN KEY (user_id) REFERENCES user (id)
);
ALTER TABLE user_info CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table address (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  title VARCHAR(60) NOT NULL,
  latitude DOUBLE,
  longitude DOUBLE,
  detail VARCHAR(255),
  phone_number VARCHAR(8),
  post_code VARCHAR(10),
  CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES user (id)
);
ALTER TABLE address CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table item_group (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(128) NOT NULL,
  icon VARCHAR(128),
  image VARCHAR(128)
);
ALTER TABLE item_group CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

INSERT INTO country (title, phoneCode) VALUES ('ایران', '+98');

INSERT INTO province (country_id, title) VALUES (1, 'تهران');

INSERT INTO city (country_id, province_id, title, phoneCode) VALUES (1, 1, 'تهران', '21');

select * from item_group;

INSERT INTO item_group (title, image) VALUES
('کاپ', 'cup.png');