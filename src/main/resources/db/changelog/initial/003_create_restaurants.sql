-- changelog Aisha: 003 create restaurant table
create table restaurants
(
    id          long auto_increment primary key,
    name        varchar(255) not null,
    description varchar(255),
    image       varchar(255),
    address     varchar(255) not null
)