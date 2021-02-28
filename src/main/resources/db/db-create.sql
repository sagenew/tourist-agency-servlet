create table if not exists users
(
    id        int8             primary key,
    username  varchar(255)     not null unique,
    password  varchar(255)     not null,
    full_name varchar(255)     not null,
    email     varchar(255)     not null,
    bio       varchar(255),
    discount  double precision not null
);

create table if not exists user_authorities
(
    user_id int8 not null references users(id) on delete cascade,
    authorities varchar(255)
);

create table if not exists tours
(
    id          int8             primary key,
    name        varchar(255)     not null,
    type        varchar(255)     not null,
    price       double precision not null,
    group_size  int              not null,
    hotel       varchar(255)     not null,
    description varchar(255),
    hot         boolean          not null
);

create table if not exists orders
(
    id       int8             primary key,
    tour_id  int8             not null references tours (id) on delete cascade,
    user_id  int8             not null references users (id) on delete cascade,
    status   varchar(255)     not null,
    price    double precision not null,
    discount double precision not null
);

create table if not exists discount
(
    id int8 primary key,
    step double precision not null,
    threshold double precision not null
);

create sequence users_id_seq;
create sequence tours_id_seq;
create sequence orders_id_seq;