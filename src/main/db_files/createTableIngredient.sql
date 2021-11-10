create table ingredient
(
    id bigserial not null primary key,
    recipe_id bigint not null references recipe(id) on delete cascade,
    name      varchar   not null,
    amount    integer   not null,
    unit      varchar   not null
);