-- changelog Aisha: 008 create order products table

create table order_products
(
    order_id   long           not null,
    product_id long           not null,
    quantity   integer        not null,
    amount     decimal(10, 2) not null,

    constraint pk_order_products
        primary key (order_id, product_id),

    constraint order_id
        foreign key (order_id)
            references orders (id)
            on delete cascade
            on update cascade,

    constraint product_id
        foreign key (product_id)
            references products (id)
            on delete cascade
            on update cascade
)