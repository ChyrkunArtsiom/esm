create schema esm_module2;

create table esm_module2.tags
(
    id   serial      not null,
    name varchar(45) not null,
    constraint tags_pk
        primary key (id)
);

create unique index tags_id_uindex
    on esm_module2.tags (id);

create unique index tags_name_uindex
    on esm_module2.tags (name);

CREATE SEQUENCE esm_module2.tags_id_seq START WITH 1 INCREMENT BY 1;

create table esm_module2.certificates
(
    id               serial                   not null,
    name             varchar(45)              not null,
    description      varchar(45)              not null,
    price            double precision         not null,
    create_date      timestamp with time zone not null,
    last_update_date timestamp with time zone,
    duration         integer                  not null,
    constraint certificates_pk
        primary key (id)
);

create unique index certificates_id_uindex
    on esm_module2.certificates (id);

create unique index certificates_name_uindex
    on esm_module2.certificates (name);

CREATE SEQUENCE esm_module2.certificates_id_seq START WITH 1 INCREMENT BY 1;

create table esm_module2.certificate_tag
(
    certificate_id integer not null,
    tag_id         integer not null,
    constraint certificate_tag_pk
        primary key (certificate_id, tag_id),
    constraint certificate_tag_tags_id_fk
        foreign key (tag_id) references esm_module2.tags
            on update cascade on delete restrict,
    constraint certificate_tag_certificates_id_fk
        foreign key (certificate_id) references esm_module2.certificates
            on update cascade on delete cascade
);

create table esm_module2.users
(
    id       serial      not null,
    name     varchar(45) not null,
    password varchar(45) not null,
    constraint users_pk
        primary key (id)
);

create unique index users_id_uindex
    on esm_module2.users (id);

create unique index users_name_uindex
    on esm_module2.users (name);

CREATE SEQUENCE esm_module2.users_id_seq START WITH 1 INCREMENT BY 1;

create table esm_module2.orders
(
    id            serial                   not null,
    cost          double precision         not null,
    purchase_date timestamp with time zone not null,
    user_id       integer                  not null,
    constraint orders_pk
        primary key (id),
    constraint orders_users_id_fk
        foreign key (user_id) references esm_module2.users
            on update cascade on delete cascade
);

create unique index orders_id_uindex
    on esm_module2.orders (id);

CREATE SEQUENCE esm_module2.orders_id_seq START WITH 1 INCREMENT BY 1;

create table esm_module2.certificate_order
(
    certificate_id integer not null,
    order_id       integer not null,
    constraint certificate_order_pk
        primary key (certificate_id, order_id),
    constraint certificate_order_certificates_id_fk
        foreign key (certificate_id) references esm_module2.certificates
            on update cascade on delete cascade,
    constraint certificate_order_orders_id_fk
        foreign key (order_id) references esm_module2.orders
            on update cascade on delete cascade
);
