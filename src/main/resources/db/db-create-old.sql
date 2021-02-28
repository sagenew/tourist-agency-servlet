CREATE TABLE if not exists users
(
    id        int8         not null,
    username  varchar(255) not null unique,
    password  varchar(255) not null,
    full_name varchar(255) not null,
    email     varchar(255) not null,
    bio       varchar(255),
    primary key (id)
);

create table if not exists user_authorities
(
    user_id     int8 not null,
    authorities varchar(255)
);

create table if not exists tours
(
    id          int8         not null,
    name        varchar(255) not null,
    type        varchar(255),
    price       int,
    group_size  int,
    hotel       varchar(255),
    description varchar(255),
    hot      boolean,
    primary key (id)
);

create table if not exists orders
(
    id      int8         not null,
    tour_id int8         not null,
    user_id int8         not null,
    status  varchar(255) not null,
    primary key (id)
);

create sequence users_id_seq;
create sequence tours_id_seq;
create sequence orders_id_seq;
