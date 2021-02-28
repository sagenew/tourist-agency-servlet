-- query.user.create
insert into users(id, username, password, full_name, email, bio, discount, enabled)
values (nextval('users_id_seq'), ?, ?, ?, ?, ?, ?, ?);

-- query.user.update
update users
set username  = ?,
    password  = ?,
    full_name = ?,
    email     = ?,
    bio       = ?
where id = ?;

-- query.user.update-enabled
update users
set enabled = ?
where id = ?;

-- query.user.delete
delete
from users
where id = ?;

-- query.user.find.by_id
select users.id        as "users.id",
       users.username  as "users.username",
       users.password  as "users.password",
       users.full_name as "users.full_name",
       users.email     as "users.email",
       users.bio       as "users.bio",
       users.enabled as "users.enabled"
from users
where users.id = ?;

-- query.user.find.by_username
select users.id        as "users.id",
       users.username  as "users.username",
       users.password  as "users.password",
       users.full_name as "users.full_name",
       users.email     as "users.email",
       users.bio       as "users.bio",
       users.enabled as "users.enabled"
from users
where users.username = ?;

-- query.user.find.all
select users.id        as "users.id",
       users.username  as "users.username",
       users.password  as "users.password",
       users.full_name as "users.full_name",
       users.email     as "users.email",
       users.bio       as "users.bio",
       users.enabled as "users.enabled"
from users;

-- query.user.find.all.pageable
select users.id        as "users.id",
       users.username  as "users.username",
       users.password  as "users.password",
       users.full_name as "users.full_name",
       users.email     as "users.email",
       users.bio       as "users.bio",
       users.enabled as "users.enabled"
from users
order by id
limit ? offset ?;

-- query.user.count.rows
select count(*)
from users;

-- query.user.join.authorities
select users.id                     as "users.id",
       user_authorities.user_id     as "user_authorities.user_id",
       user_authorities.authorities as "user_authorities.authorities"
from users
         left join user_authorities on users.id = user_authorities.user_id
where users.id = ?;

-- query.user.join.orders+tours
select users.id          as "users.id",
       orders.id         as "orders.id",
       orders.tour_id    as "orders.tour_id",
       orders.user_id    as "orders.user_id",
       orders.status     as "orders.status",
       tours.id          as "tours.id",
       tours.name        as "tours.name",
       tours.type        as "tours.type",
       tours.price       as "tours.price",
       tours.group_size  as "tours.group_size",
       tours.hotel       as "tours.hotel",
       tours.description as "tours.description",
       tours.hot         as "tours.hot"
from users
         left join orders on users.id = orders.user_id
         left join tours on orders.tour_id = tours.id
where users.id = ?
order by "orders.id" desc;

