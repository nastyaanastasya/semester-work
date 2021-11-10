create table recipe
(
    id bigserial not null primary key,
    title varchar not null,
    time_of_cooking integer not null,
    description text not null,
    user_id bigint not null references "user"(id),
    date timestamp default CURRENT_TIMESTAMP not null
);