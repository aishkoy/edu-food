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
);

insert into users(name, surname, email, password, address, avatar, enabled, role_id)
values ('Ivan', 'Ivanov', 'qwe@qwe.qwe', '$2a$12$vqRuFc/bqqQri/oukiZLZulTCQ6dfq1nbzptLhsQbwnasaLjvy2uW', 'Бишкек', null,
        true, (select id from ROLES where name = 'USER')),
       ('Aisha', 'Orozbekova', 'ewq@ewq.ewq', '$2a$12$vqRuFc/bqqQri/oukiZLZulTCQ6dfq1nbzptLhsQbwnasaLjvy2uW', 'Бишкек', null,
        true, (select id from ROLES where name = 'USER'));

