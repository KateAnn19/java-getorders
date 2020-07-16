package local.mcgeeka.getorders31.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import local.mcgeeka.getorders31.models.Customer;
import local.mcgeeka.getorders31.models.Order;
import local.mcgeeka.getorders31.models.Payment;
import local.mcgeeka.getorders31.repositories.AgentsRepository;
import local.mcgeeka.getorders31.repositories.OrdersRepository;
import local.mcgeeka.getorders31.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service(value = "orderService")
public class OrderServicesImpl implements OrderService
{
    @Autowired
    private OrdersRepository ordersrepos;

    @Autowired
    private PaymentRepository paysrepos;

//    @Override
//    public Order save(Order order)
//    {
//        return ordersrepos.save(order);
//    }

//new version of save method
    @Transactional
    @Override
    public Order save(Order order)
    {
        //this will do both a PUT and POST
        Order newOrder = new Order(); //create a new order
        if(order.getOrdnum() != 0) //this means it's there
        {
            //check to see if it's valid
            ordersrepos.findById(order.getOrdnum()).orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found!"));
            newOrder.setOrdnum(order.getOrdnum());
        }

        //all these fields are a single entity
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setCustomerfoo(order.getCustomerfoo());
        newOrder.setOrderdescription(order.getOrderdescription());
        //all these fields are a single entity

        //payments -- payments are a little different because they exist on their own
        //ManyToMany
        newOrder.getPaymentsfoo().clear();
        for(Payment p : order.getPaymentsfoo())
        {
            //we don't need to create a new object we need to FIND an object to add
            Payment newPay = paysrepos.findById(p.getPaymentid()).orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));//else throw says this is an invalid id
            newOrder.getPaymentsfoo().add(newPay);
        }
        return ordersrepos.save(newOrder); //if you send this with ID of 0 it tries to add // any other ID it replaces
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
