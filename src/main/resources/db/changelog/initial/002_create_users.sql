-- changelog Aisha: 002 create user table
create table users
(
    id       long auto_increment primary key not null,
    name     varchar(100)                    not null,
    surname  varchar(100),
    email    varchar(255)                    not null unique,
    password varchar(255)                    not null,
    address  varchar(255)                    not null,
    avatar   varchar(255),
    enabled  boolean                         not null,
    role_id  long                            not null,

    constraint fk_role_id
        foreign key (role_id)
            references roles (id)
            on delete cascade
            on update cascade
)