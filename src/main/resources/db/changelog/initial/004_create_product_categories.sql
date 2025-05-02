-- changelog Aisha: 004 create product categories table
create table product_categories(
    id long auto_increment primary key not null,
    name varchar(55) not null unique
);

insert into product_categories(name)
values ('Закуски'),
       ('Салаты'),
       ('Супы'),
       ('Горячие блюда'),
       ('Гарниры'),
       ('Десерты'),
       ('Напитки'),
       ('Алкогольные напитки'),
       ('Хлебобулочные изделия'),
       ('Специальные предложения');