create table user_details
(
    id               text not null
        primary key,
    full_name        text not null,
    email            text not null
        unique,
    employee_id      text not null,
    user_type        text not null,
    deleted          boolean default false,
    blocked          boolean default false,
    active           boolean default false,
    roles            jsonb,
    branch           jsonb,
    created_by       jsonb,
    created_on       timestamp,
    last_modified_by jsonb,
    last_modified_on timestamp,
    user_image       text,
    user_position    text,
    selected_theme   text
);

create table roles
(
    id               text      not null
        primary key,
    title            text,
    permissions      jsonb,
    created_on       timestamp not null,
    created_by       jsonb     not null,
    last_modified_on timestamp,
    last_modified_by jsonb,
    deleted          boolean   not null,
    status           text,
    active           boolean default true
);

create table user_credential
(
    id                 text not null
        primary key,
    user_id            text,
    max_login_attempts integer default 5,
    login_attempts     integer default 0,
    password           text not null,
    generated_salt     text,
    password_history   jsonb,
    created_by         jsonb,
    created_on         timestamp,
    last_modified_by   jsonb,
    last_modified_on   timestamp,
    deleted            boolean default false
);

