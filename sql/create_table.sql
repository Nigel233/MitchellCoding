CREATE DATABASE 'demo';
USE demo;

create table vehicles (
	id  int(3) NOT NULL AUTO_INCREMENT,
	year varchar(4) NOT NULL,
	make varchar(100) NOT NULL,
	model varchar(100),
	PRIMARY KEY (id)
);

