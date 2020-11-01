create schema esm_module2;

create table esm_module2.tags
(
    id   serial      not null
        constraint tags_pk
            primary key,
    name varchar(45) not null
);

/*alter table esm_module2.tags
    owner to esm_user;*/

create unique index tags_id_uindex
    on esm_module2.tags (id);

create unique index tags_name_uindex
    on esm_module2.tags (name);

create table esm_module2.certificates
(
    id               serial                   not null
        constraint certificates_pk
            primary key,
    name             varchar(45)              not null,
    description      varchar(45)              not null,
    price            double precision         not null,
    create_date      timestamp with time zone not null,
    last_update_date timestamp with time zone,
    duration         integer                  not null
);

/*alter table esm_module2.certificates
    owner to esm_user;*/

create unique index certificates_id_uindex
    on esm_module2.certificates (id);

create unique index certificates_name_uindex
    on esm_module2.certificates (name);

create table esm_module2.certificate_tag
(
    certificate_id integer not null
        constraint certificate_tag_certificates_id_fk
            references esm_module2.certificates
            on update cascade on delete cascade,
    tag_id         integer not null
        constraint certificate_tag_tags_id_fk
            references esm_module2.tags
            on update cascade on delete restrict,
    constraint certificate_tag_pk
        primary key (certificate_id, tag_id)
);

/*alter table esm_module2.certificate_tag
    owner to esm_user;*/