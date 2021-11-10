create table "user"
(
    id bigserial not null primary key,
    username  varchar not null,
    email varchar not null,
    password_hash varchar not null,
    uuid varchar,
    date_of_register date default CURRENT_TIMESTAMP not null,
    profile_img_id bigint references media(id) on delete set null
);