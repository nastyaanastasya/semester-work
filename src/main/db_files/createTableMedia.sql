create table media
(
    id bigserial not null primary key,
    filename  varchar   not null,
    mime_type varchar   not null
);