-- changelog Aisha: 007 create order table

create table orders
(
    id             long auto_increment primary key not null,
    user_id        long,
    status_id      long                            not null,
    total_amount   decimal(10, 2)                  not null,
    total_quantity integer                         not null,
    date           timestamp,

    constraint fk_status_id
        foreign key (status_id)
            references order_statuses (id)
            on delete restrict
            on update cascade,

    constraint fk_user_id
        foreign key (user_id)
            references users (id)
            on delete cascade
            on update cascade
)