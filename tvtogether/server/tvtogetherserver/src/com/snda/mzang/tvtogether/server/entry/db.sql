create database `tvtogetherdb`;

use `tvtogetherdb`

create table user_info(
id varchar(128) not null,
username varchar(128) not null,
user_password varchar(128) not null,
icon varchar(128) not null default '',
comments varchar (1024) not null default '',
favor varchar(1024) not null default '',
location_x double default 0,
location_y double default 0,
register_time datetime,
last_login datetime,
status varchar(128) not null default 'enabled',
PRIMARY KEY (`id`)
);

create table channel(
id varchar(128) not null,
name varchar(128) not null,
comments varchar(1024) not null,
status varchar(1024) not null,
image varchar(1024) not null,
update_time datetime,
PRIMARY KEY (`id`)
)
















