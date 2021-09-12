create table if not exists task
(
    id                 int8         not null,
    author_id          int8         not null,
    description        varchar(255) not null,
    is_completed       boolean      not null,
    status_description varchar(255) not null,
    primary key (id)
);
create table if not exists user_role
(
    user_id int8         not null,
    roles   varchar(255) not null
);
create table if not exists usr
(
    id       int8         not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

alter table if exists usr
    add constraint usrnameindex unique (username);
alter table if exists task
    add constraint task_usr_fk foreign key (author_id) references usr;
alter table if exists user_role
    add constraint user_role_usr_fk foreign key (user_id) references usr;

create sequence task_seq increment 1 minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;

create sequence user_seq increment 1 minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;