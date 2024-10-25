create table products(
    id bigint not null primary key,
    product_name varchar(255) not null ,
    price decimal(10, 2)
);

alter table products owner to root;
create sequence product_id_seq;
alter sequence product_id_seq owner to root;