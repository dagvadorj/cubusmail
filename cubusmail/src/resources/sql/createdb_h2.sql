DROP SEQUENCE IF EXISTS CUBUSMAIL_SEQ;
CREATE SEQUENCE CUBUSMAIL_SEQ START WITH 1000;


DROP TABLE IF EXISTS USERS; 
CREATE TABLE USERS (
	ID bigint PRIMARY KEY NOT NULL,
	USERNAME varchar(255) NOT NULL,
	PREFERENCES varchar(10000) NOT NULL,
	CREATED datetime,
	LASTLOGIN datetime
);	


DROP TABLE IF EXISTS IDENTITIES; 
CREATE TABLE IDENTITIES
(
   ID bigint PRIMARY KEY NOT NULL,
   BCC varchar(255),
   DISPLAYNAME varchar(255),
   EMAIL varchar(255),
   HTML_SIGNATURE boolean NOT NULL,
   ORGANIZATION varchar(255),
   REPLY_TO varchar(255),
   SIGNATURE varchar(10000),
   STANDARD boolean NOT NULL,
   USER_ACCOUNT_ID bigint,
   FOREIGN KEY (USER_ACCOUNT_ID) REFERENCES USERS(ID)
);


DROP TABLE IF EXISTS ADDRESSFOLDERS; 
CREATE TABLE ADDRESSFOLDERS
(
   ID bigint PRIMARY KEY NOT NULL,
   NAME varchar(255),
   TYPE integer,
   USER_ACCOUNT_ID bigint,
   FOREIGN KEY (USER_ACCOUNT_ID) REFERENCES USERS(ID)
);


DROP TABLE IF EXISTS ADDRESSES; 
CREATE TABLE ADDRESSES
(
   ID bigint PRIMARY KEY NOT NULL,
   FIRSTNAME varchar(128),
   middleName varchar(128),
   lastName varchar(128),
   title varchar(128),
   birthDate date,
   company varchar(128),
   position varchar(128),
   department varchar(128),
   email varchar(255),
   email2 varchar(255),
   email3 varchar(255),
   email4 varchar(255),
   email5 varchar(255),
   im varchar(255),
   url varchar(255),
   privatePhone varchar(128),
   workPhone varchar(128),
   privateMobile varchar(128),
   workMobile varchar(128),
   privateFax varchar(128),
   workFax varchar(128),
   pager varchar(128),
   privateStreet varchar(128),
   privateZipcode varchar(128),
   privateCity varchar(128),
   privateState varchar(128),
   privateCountry varchar(128),
   workStreet varchar(128),
   workZipcode varchar(128),
   workCity varchar(128),
   workState varchar(128),
   workCountry varchar(128),
   notes varchar(10000),
   ADDRESS_FOLDER_ID bigint,
   FOREIGN KEY (ADDRESS_FOLDER_ID) REFERENCES ADDRESSFOLDERS(ID)
);
