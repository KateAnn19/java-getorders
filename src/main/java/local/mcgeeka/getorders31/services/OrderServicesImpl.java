package local.mcgeeka.getorders31.services;

import local.mcgeeka.getorders31.models.Customer;
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

//    @Override
//    public Order save(Order order)
//    {
//        return ordersrepos.save(order);
//    }

//new version of save method
    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        //this will do both a PUT and POST
        Customer newCustomer = new Customer(); //create a new restaurant

        if(customer.getCustcode() != 0) //this means it's there
        {
            //check to see if it's valid
            custrepos.findById(customer.getCustcode()).orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " not found!"));
            newCustomer.setCustcode(customer.getCustcode());
        }
        //all these fields are a single entity
        newRestaurant.setName(restaurant.getName());
        newRestaurant.setAddress(restaurant.getAddress());
        newRestaurant.setCity(restaurant.getCity());
        newRestaurant.setState(restaurant.getState());
        newRestaurant.setTelephone(restaurant.getTelephone());
        newRestaurant.setSeatcapacity(restaurant.getSeatcapacity());
        //all these fields are a single entity

        //two collections also exist - payments and menus
        //menu
        //OneToMany
        newRestaurant.getMenus().clear(); //start with fresh list  -- JUST IN CASE CLEAR IT OUT
        for(Menu m : restaurant.getMenus()) //cycle through list
        {
            Menu newMenu = new Menu(m.getDish(), m.getPrice(), newRestaurant); //dish, price, restaurant  -- we don't care if they send an id (we ignore it)
            newRestaurant.getMenus().add(newMenu);
        }

        //payments -- payments are a little different because they exist on their own
        //ManyToMany
        newRestaurant.getPayments().clear();
        for(Payment p : restaurant.getPayments())
        {
            //we don't need to create a new object we need to FIND an object to add
            Payment newPay = payrepos.findById(p.getPaymentid()).orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));//else throw says this is an invalid id
            newRestaurant.getPayments().add(newPay);
        }

        return restrepos.save(newRestaurant); //if you send this with ID of 0 it tries to add // any other ID it replaces
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
