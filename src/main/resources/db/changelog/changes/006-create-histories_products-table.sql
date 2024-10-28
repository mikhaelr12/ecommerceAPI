create table histories_products(
    history_id bigint,
    product_id bigint,
    primary key(history_id,product_id),
    foreign key (history_id) references histories (id) on delete cascade ,
    foreign key (product_id) references products (id) on delete cascade

);