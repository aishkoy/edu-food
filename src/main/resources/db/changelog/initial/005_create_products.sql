-- changelog Aisha: 005 create product table

create table products
(
    id            long auto_increment primary key not null,
    name          varchar(255)                    not null,
    description   varchar(255),
    category_id   long                            not null,
    price         decimal(10, 2)                  not null,
    image         varchar(255),
    restaurant_id long                            not null,

    constraint fk_category_id
        foreign key (category_id)
            references product_categories(id)
            on delete restrict
            on update cascade,

    constraint fk_restaurant_id
        foreign key (restaurant_id)
            references restaurants (id)
            on delete cascade
            on update cascade
)