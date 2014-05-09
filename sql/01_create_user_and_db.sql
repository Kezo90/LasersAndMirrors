CREATE USER 'lasersandmirrors'@'localhost' IDENTIFIED BY '123';
CREATE DATABASE `lasers_and_mirrors` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL PRIVILEGES ON `lasers_and_mirrors`.* TO 'lasersandmirrors'@'localhost' WITH GRANT OPTION;