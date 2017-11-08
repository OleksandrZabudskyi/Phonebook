# Phonebook

- Stack of technologies: Spring(Boot, MVC, Security, Data), JQuery, Bootstrap, JSON, JSP, Maven, MySQL, Junit, Mockito

# Data Base entities:
- `User`: login, password, full name
- `Contact`: first name, last name, additional name, mobile phone, home phone, address, email

# DB scheme for MySQL: 

CREATE DATABASE  IF NOT EXISTS `phonebook`;
USE `phonebook`;

DROP TABLE IF EXISTS `contact`;
DROP TABLE IF EXISTS `user`;

-- Table structure for table `user`

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Table structure for table `contact`

CREATE TABLE `contact` (
  `contact_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `additional_name` varchar(45) NOT NULL,
  `mobile_phone` varchar(45) NOT NULL,
  `home_phone` varchar(45) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`contact_id`),
  UNIQUE KEY `contact_id_UNIQUE` (`contact_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `SPRING_SESSION_ATTRIBUTES`;
DROP TABLE IF EXISTS `SPRING_SESSION`;

-- Table structure for table `spring_session`

CREATE TABLE SPRING_SESSION (
  SESSION_ID CHAR(36),
  CREATION_TIME BIGINT NOT NULL,
  LAST_ACCESS_TIME BIGINT NOT NULL,
  MAX_INACTIVE_INTERVAL INT NOT NULL,
  PRINCIPAL_NAME VARCHAR(100),
  CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (SESSION_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (LAST_ACCESS_TIME);

-- Table structure for table `spring_session_attributes`

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
  SESSION_ID CHAR(36),
  ATTRIBUTE_NAME VARCHAR(200),
  ATTRIBUTE_BYTES BLOB,
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_ID, ATTRIBUTE_NAME),
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_ID) REFERENCES SPRING_SESSION(SESSION_ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (SESSION_ID);

# Web aplication pages:
  1. Login page
  2. Registration
  3. Home page include phone book for logged in user. This page contains popup forms for adding new contact, editing and deleting existed contact and possibility to filter contacts by any criteria.

# DB storage:
- `MYSQL`
- `JSON file`

# Deployment
The project is deployed with VM options: -Dphonebook.conf=/path/to/file.properties
- Example: java -jar -Dphonebook.conf=/path/to/file.properties phonebook.war
  	
	
     
	   
