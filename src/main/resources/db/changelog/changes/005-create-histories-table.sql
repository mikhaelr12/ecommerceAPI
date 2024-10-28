create table histories(
    id bigint primary key not null,
    user_id bigint not null ,
    total_price decimal(10, 2),
    finished_at timestamp,
    status varchar(255),
    constraint FK_USER_HISTORY foreign key (user_id) references users (id)
);