CREATE TABLE users (
         id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
         username VARCHAR(100),
         password VARCHAR(100),
         token TINYINT(1)
       );