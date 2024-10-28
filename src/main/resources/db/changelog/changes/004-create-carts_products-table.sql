create table carts_products(
    product_id bigint not null,
    cart_id bigint not null,
    primary key (cart_id, product_id),
    foreign key (product_id) references products(id) on delete cascade,
    foreign key (cart_id) references carts(id) on delete cascade
);