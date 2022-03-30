DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Restaurant;
DROP TABLE IF EXISTS Rating_details;
DROP TABLE IF EXISTS Restaurant_details;

CREATE TABLE `USERS` (
  `user_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
);

CREATE TABLE Restaurant_details ( 
details_id INT PRIMARY KEY NOT NULL,
image_url VARCHAR(150) NOT NULL,
address VARCHAR(75) NOT NULL,
phone_no VARCHAR(20) NOT NULL,
estimated_price VARCHAR(20) NOT NULL,
yelp_url VARCHAR(250) NOT NULL
);

CREATE TABLE Rating_details ( 
rating_id INT PRIMARY KEY NOT NULL,
review_count INT NOT NULL,
rating VARCHAR(10) NOT NULL
);

CREATE TABLE Restaurant ( 
restaurant_id VARCHAR(75) PRIMARY KEY NOT NULL,
restaurant_name VARCHAR(75) NOT NULL,
details_id INT NOT NULL,
rating_id INT NOT NULL,
FOREIGN KEY (details_id) REFERENCES Restaurant_details(details_id), 
FOREIGN KEY (rating_id) REFERENCES Rating_details(rating_id) 
);

CREATE TABLE Category ( 
category_id INT PRIMARY KEY NOT NULL,
category_name VARCHAR(75) NOT NULL,
restaurant_id VARCHAR(75) NOT NULL,
FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id)
);