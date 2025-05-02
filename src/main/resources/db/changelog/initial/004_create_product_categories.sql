-- changelog Aisha: 004 create product categories table
create table product_categories(
    id long auto_increment primary key not null,
    name varchar(55) not null unique,
    icon varchar(50)
);

insert into product_categories(name, icon)
values ('Закуски', 'sandwich'),
       ('Салаты', 'salad'),
       ('Супы', 'soup'),
       ('Горячие блюда', 'utensils'),
       ('Гарниры', 'cooking-pot'),
       ('Десерты', 'cake'),
       ('Напитки', 'coffee');