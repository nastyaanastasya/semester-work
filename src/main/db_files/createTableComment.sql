create table comment
(
    id bigserial primary key,
    rating smallint not null,
    review text,
    user_id  bigint not null references "user"(id),
    recipe_id bigint not null references recipe(id),
    date timestamp default CURRENT_TIMESTAMP not null
);