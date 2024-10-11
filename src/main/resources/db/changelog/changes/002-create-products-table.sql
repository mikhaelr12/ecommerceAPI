create table products(
    id bigint not null primary key,
    product_name varchar(255) not null ,
    price double precision
);

alter table products owner to root;
create sequence products_id_seq;
alter sequence products_id_seq owner to root;