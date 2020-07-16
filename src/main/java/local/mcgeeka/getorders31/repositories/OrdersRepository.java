package local.mcgeeka.getorders31.repositories;

import local.mcgeeka.getorders31.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Order, Long>
{
}
