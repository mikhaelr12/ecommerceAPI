create table carts(
    id bigint not null primary key,
    total decimal(10, 2),
    status varchar(255),
    user_id bigint,
    constraint FK_USER_CART foreign key (user_id) references users (id)
);

alter table carts owner to root;
create sequence cart_id_seq;
alter sequence cart_id_seq owner to root;