create sequence if not exists hibernate_sequence start 1 increment 1;

create table if not exists role
(
    id   int8        not null ,
    name varchar(50) not null ,
    primary key (id)
);

create table if not exists task
(
    id             int8         not null,
    author_id      int8         not null ,
    completed_date varchar(100) not null,
    description    varchar(255) not null,
    is_completed   boolean      not null,
    primary key (id)
);

create table if not exists usr
(
    id       int8         not null,
    password varchar(255)  not null,
    username varchar(255) not null,
    primary key (id)
);

create table if not exists usr_roles
(
    users_id int8 not null,
    roles_id int8 not null,
    primary key (users_id, roles_id)
);

alter table if exists usr
    add constraint usrnameindex unique (username);

alter table if exists task
    add constraint task_usr_fk foreign key (author_id) references usr;

alter table if exists usr_roles
    add constraint usr_roles_role_fk foreign key (roles_id) references role;

alter table if exists usr_roles
    add constraint usr_roles_usr_fk foreign key (users_id) references usr;