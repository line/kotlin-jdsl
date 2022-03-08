create table test_delivery
(
    id         bigint not null auto_increment,
    order_id   bigint,
    address_id bigint,
    primary key (id)
);

create table test_delivery_address
(
    id             bigint not null auto_increment,
    base_address   varchar(255),
    detail_address varchar(255),
    district       varchar(255),
    province       varchar(255),
    zip_code       varchar(255),
    primary key (id)
);

create table test_delivery_item
(
    id            bigint not null auto_increment,
    order_item_id bigint,
    delivery_id   bigint,
    primary key (id)
);

create table test_order
(
    id           bigint not null auto_increment,
    purchaser_id bigint,
    primary key (id)
);

create table test_order_address
(
    id             bigint not null auto_increment,
    base_address   varchar(255),
    detail_address varchar(255),
    district       varchar(255),
    province       varchar(255),
    zip_code       varchar(255),
    primary key (id)
);

create table test_order_group
(
    id         bigint not null auto_increment,
    address_id bigint,
    order_id   bigint,
    primary key (id)
);

create table test_order_item
(
    id            bigint not null auto_increment,
    claimed       bit,
    price         decimal(19, 2),
    product_id    bigint,
    product_image varchar(255),
    product_name  varchar(255),
    quantity      bigint,
    group_id      bigint,
    primary key (id)
);

create table test_table
(
    sequence bigint not null auto_increment,
    id       bigint,
    amount   bigint,
    role     varchar(255),
    primary key (sequence)
);
create index idx1 on test_order (purchaser_id, id);

alter table test_delivery
    add constraint FKbknoveq128lawjbrbei4t3a2q
        foreign key (address_id)
            references test_delivery_address (id);

alter table test_delivery_item
    add constraint FKivfo09ro6pp0guhp2ubt3w3m0
        foreign key (delivery_id)
            references test_delivery (id);

alter table test_order_group
    add constraint FKawl6uo7uexjk2dxv4o7sus6a3
        foreign key (address_id)
            references test_order_address (id);

alter table test_order_group
    add constraint FKb2eqd4j6kfd9cclwpjj9krovn
        foreign key (order_id)
            references test_order (id);

alter table test_order_item
    add constraint FKnt266ga1e5o0qfsjyk557t38f
        foreign key (group_id)
            references test_order_group (id);