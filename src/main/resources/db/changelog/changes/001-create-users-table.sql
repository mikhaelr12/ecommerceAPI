create table users(
    id bigint primary key not null,
    username varchar(255) unique not null,
    password varchar(255) not null,
    role varchar(255)
);

alter table users owner to root;
create sequence user_id_seq;
alter sequence user_id_seq owner to root;