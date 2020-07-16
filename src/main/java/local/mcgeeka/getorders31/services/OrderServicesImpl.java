package local.mcgeeka.getorders31.services;

import local.mcgeeka.getorders31.models.Order;
import local.mcgeeka.getorders31.repositories.AgentsRepository;
import local.mcgeeka.getorders31.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
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

    @Override
    public List<Order> findAllOrders()
    {
        List<Order> rtnList = new ArrayList<>();
        ordersrepos.findAll().iterator().forEachRemaining(rtnList::add);
        return rtnList;
    }

    @Override
    public Order findByOrderNum(long id)
    {
        return ordersrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order " + id + "Not Found"));
    }

    @Transactional
    @Override
    public void delete(long orderid)
    {
        ordersrepos.findById(orderid).orElseThrow(() -> new EntityNotFoundException("Order " + orderid + " Not Found!"));
        //first search to see if the id can be found (is it a valid id?) ...if it isn't an exception is thrown
        //the first part helps with data validation ... it checks first even though you don't "have" to
        ordersrepos.deleteById(orderid);
    }
}
