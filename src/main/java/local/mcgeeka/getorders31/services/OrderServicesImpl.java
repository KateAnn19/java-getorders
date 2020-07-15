package local.mcgeeka.getorders31.services;

import local.mcgeeka.getorders31.models.Order;
import local.mcgeeka.getorders31.repositories.AgentsRepository;
import local.mcgeeka.getorders31.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "orderService")
public class OrderServicesImpl implements OrderService
{
    @Autowired
    private OrdersRepository ordersrepos;

    @Override
    public Order save(Order order)
    {
        return ordersrepos.save(order);
    }
}
