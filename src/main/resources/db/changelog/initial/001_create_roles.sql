-- changelog Aisha: 001 create role table
create table roles
(
    id   long auto_increment primary key not null,
    name varchar(50)                     not null unique
);

insert into roles(name)
values ( 'USER' ),
       ('ADMIN');