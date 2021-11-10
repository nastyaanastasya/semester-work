create table recipe_media
(
    id bigserial not null primary key,
    recipe_id bigint not null references recipe(id) on delete cascade,
    media_id  bigint not null references media(id) on delete cascade
);