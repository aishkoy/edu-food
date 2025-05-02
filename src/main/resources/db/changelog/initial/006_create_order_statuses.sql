-- changelog Aisha: 006 create order status table
create table order_statuses(
    id long auto_increment primary key not null,
    name varchar(55) not null unique
);

insert into order_statuses(name)
values ('Корзина'),
       ('Оплачен');