package local.mcgeeka.getorders31.services;

import local.mcgeeka.getorders31.models.Order;

import java.util.List;

public interface OrderService
{
    Order save(Order order);
    List<Order> findAllOrders();
    Order findByOrderNum(long id);

    void delete(long orderid);
}
