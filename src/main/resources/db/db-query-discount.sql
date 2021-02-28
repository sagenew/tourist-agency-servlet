-- query.discount.get
select discount.step      as "discount.step",
       discount.threshold as "discount.threshold"
from discount
where id = 1;

-- query.discount.set
update discount
set step      = ?,
    threshold = ?
where id = 1;

