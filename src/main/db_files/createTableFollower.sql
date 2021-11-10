create table follower
(
    id  bigserial not null primary key,
    user_id bigint not null references "user"(id) on delete cascade,
    follower_id bigint not null references "user" on delete cascade
);