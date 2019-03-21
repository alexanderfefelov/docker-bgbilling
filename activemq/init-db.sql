drop user if exists activemq;
drop database if exists activemq;
create database activemq character set utf8;
create user 'activemq'@'%' identified by 'activemq';
grant all privileges on activemq.* to 'activemq'@'%';
