create table liked_recipe
(
    id bigserial not null primary key,
    recipe_id bigint not null references recipe(id) on delete cascade,
    user_id bigint not null references "user" on delete cascade
);