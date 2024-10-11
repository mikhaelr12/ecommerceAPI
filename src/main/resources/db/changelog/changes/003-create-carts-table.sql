create table carts(
    id bigint not null primary key,
    total double precision,
    status varchar(255)

);

alter table carts owner to root;
create sequence carts_id_seq;
alter sequence carts_id_seq owner to root;