-- query.order.create
insert into orders(id, tour_id, user_id, status, price, discount)
values (nextval('orders_id_seq'), ?, ?, ?, ?, ?);

-- query.order.update
update orders
set tour_id  = ?,
    user_id  = ?,
    status   = ?,
    price    = ?,
    discount = ?
where id = ?;

-- query.order.update-discount-by-user
update orders
set discount = ?
where user_id = ?
  and status = ?;

-- query.order.delete
delete
from orders
where id = ?;

-- query.order.find.by_id
select orders.id       as "orders.id",
       orders.tour_id  as "orders.tour_id",
       orders.user_id  as "orders.user_id",
       orders.status   as "orders.status",
       orders.price    as "orders.price",
       orders.discount as "orders.discount"
from orders
where orders.id = ?;

-- query.order.find.by_tour_id_and_user_id
select orders.id       as "orders.id",
       orders.tour_id  as "orders.tour_id",
       orders.user_id  as "orders.user_id",
       orders.status   as "orders.status",
       orders.price    as "orders.price",
       orders.discount as "orders.discount"
from orders
where orders.tour_id = ?
  and orders.user_id = ?;

-- query.order.find.all
select orders.id       as "orders.id",
       orders.tour_id  as "orders.tour_id",
       tours.name      as "tours.name",
       orders.user_id  as "orders.user_id",
       users.username  as "users.username",
       orders.status   as "orders.status",
       orders.price    as "orders.price",
       orders.discount as "orders.discount"
from orders
         left join tours on orders.tour_id = tours.id
         left join users on orders.user_id = users.id;

-- query.order.find.all.pageable
select orders.id       as "orders.id",
       orders.tour_id  as "orders.tour_id",
       orders.user_id  as "orders.user_id",
       orders.status   as "orders.status",
       orders.price    as "orders.price",
       orders.discount as "orders.discount"
from orders
order by orders.id desc
limit ? offset ?;

-- query.order.count.rows
select count(*)
from orders;

-- query.order.join.tour
select orders.id         as "orders.id",
       tours.id          as "tours.id",
       tours.name        as "tours.name",
       tours.type        as "tours.type",
       tours.price       as "tours.price",
       tours.group_size  as "tours.group_size",
       tours.hotel       as "tours.hotel",
       tours.description as "tours.description",
       tours.hot         as "tours.hot"
from orders
         left join tours on orders.tour_id = tours.id
where orders.id = ?;

-- query.order.join.user
select orders.id                    as "orders.id",
       users.id                     as "users.id",
       users.username               as "users.username",
       users.password               as "users.password",
       users.full_name              as "users.full_name",
       users.email                  as "users.email",
       users.bio                    as "users.bio",
       users.discount               as "users.discount",
       users.enabled                as "users.enabled",
       user_authorities.user_id     as "user_authorities.user_id",
       user_authorities.authorities as "user_authorities.authorities"
from orders
         left join users on orders.user_id = users.id
         left join user_authorities on users.id = user_authorities.user_id
where orders.id = ?
