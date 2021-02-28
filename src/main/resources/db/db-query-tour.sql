-- query.tour.create
insert into tours
values (nextval('tours_id_seq'), ?, ?, ?, ?, ?, ?, ?);

-- query.tour.delete
delete
from tours
where id = ?;

-- query.tour.update
update tours
set name        = ?,
    type        = ?,
    price       = ?,
    group_size  = ?,
    hotel       = ?,
    description = ?
where id = ?;

-- query.tour.update_hot
update tours
set hot = ?
where id = ?;

-- query.tour.find.by_id
select tours.id          as "tours.id",
       tours.name        as "tours.name",
       tours.type        as "tours.type",
       tours.price       as "tours.price",
       tours.group_size  as "tours.group_size",
       tours.hotel       as "tours.hotel",
       tours.description as "tours.description",
       tours.hot         as "tours.hot"
from tours
where tours.id = ?;

-- query.tour.find.all
select tours.id          as "tours.id",
       tours.name        as "tours.name",
       tours.type        as "tours.type",
       tours.price       as "tours.price",
       tours.group_size  as "tours.group_size",
       tours.hotel       as "tours.hotel",
       tours.description as "tours.description",
       tours.hot         as "tours.hot"
from tours;

-- query.tour.find.all.pageable
select tours.id          as "tours.id",
       tours.name        as "tours.name",
       tours.type        as "tours.type",
       tours.price       as "tours.price",
       tours.group_size  as "tours.group_size",
       tours.hotel       as "tours.hotel",
       tours.description as "tours.description",
       tours.hot         as "tours.hot"
from tours
order by hot desc
limit ? offset ?;

-- query.tour.count.rows
select count(*)
from tours;

-- query.tour.join.orders
select tours.id        as "tours.id",
       orders.id       as "orders.id",
       orders.tour_id  as "orders.tour_id",
       orders.user_id  as "orders.user_id",
       orders.status   as "orders.status",
       orders.price    as "orders.price",
       orders.discount as "orders.discount"
from tours
         left join orders on tours.id = orders.tour_id
         left join users on orders.user_id = users.id
where tours.id = ?;

