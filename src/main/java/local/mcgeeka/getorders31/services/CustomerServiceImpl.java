package local.mcgeeka.getorders31.services;

import local.mcgeeka.getorders31.models.Customer;
import local.mcgeeka.getorders31.repositories.AgentsRepository;
import local.mcgeeka.getorders31.repositories.CustomersRepository;
import local.mcgeeka.getorders31.views.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService
{
    @Autowired
    private CustomersRepository custrepos;


    @Override
    public Customer save(Customer customer)
    {
        return custrepos.save(customer);
    }

    @Override
    public Customer findByCustomerCode(long id)
    {
        return custrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + id + "Not Found"));
    }

    @Override
    public List<Customer> findByNameLike(String thename)
    {
        return custrepos.findByCustnameContainingIgnoringCase(thename);
    }

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> rtnList = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(rtnList::add);
        return rtnList;
    }

    @Override
    public List<CustomerOrder> getOrderCount()
    {
        return custrepos.getOrderCount();
    }


    @Transactional
    @Override
    public void delete(long custid)
    {
        custrepos.findById(custid).orElseThrow(() -> new EntityNotFoundException("Customer " + custid + " Not Found!"));
        //first search to see if the id can be found (is it a valid id?) ...if it isn't an exception is thrown
        //the first part helps with data validation ... it checks first even though you don't "have" to
        custrepos.deleteById(custid);
    }

    @Override
    public Customer update(Customer customer, long custcode)
    {
        return null;
    }


    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant)
    {
        //this will do both a PUT and POST
        Restaurant newRestaurant = new Restaurant(); //create a new restaurant

        if(restaurant.getRestaurantid() != 0) //this means it's there
        {
            //check to see if it's valid
            restrepos.findById(restaurant.getRestaurantid()).orElseThrow(() -> new EntityNotFoundException("Restaurant " + restaurant.getRestaurantid() + " not found!"));
            newRestaurant.setRestaurantid(restaurant.getRestaurantid());
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

    @Transactional //if any process fails the whole process fails (if changing data at all use this)
    @Override
    public void deleteAll()
    {
        restrepos.deleteAll();
    }

    @Transactional
    @Override
    public void delete(long restid)
    {
        restrepos.findById(restid).orElseThrow(() -> new EntityNotFoundException("Restaurant " + restid + " Not Found!"));
        //first search to see if the id can be found (is it a valid id?) ...if it isn't an exception is thrown
        //the first part helps with data validation ... it checks first even though you don't "have" to
        restrepos.deleteById(restid);
    }

    //we copied and pasted from the Save method
    //changing data needs to be transactional
    //1) grab current restaurant
    //2) get rid of if statement
    //3) get rid of the new restaurant
    //4) find current restuarant based off id sent in
    //5) refactor w/ currRestaurant
    //6) if client sent something to change then change it
    //6) add if statements
    //7) handle base data types
    //8) handle collections (if they send any do a complete replace)
    //9) if statement to check size and do a complete replacement
    @Transactional
    @Override
    public Restaurant update(Restaurant restaurant, long restid)
    {
        Restaurant currRestaurant = restrepos.findById(restid).orElseThrow(() -> new EntityNotFoundException("Restaurant " + restid + " not found!"));
        //all these fields are a single entity
        if(restaurant.getName() != null)
        {
            currRestaurant.setName(restaurant.getName());
        }

        if(restaurant.getAddress() !=  null)
        {
            currRestaurant.setAddress(restaurant.getAddress());
        }

        if(restaurant.getCity() != null)
        {
            currRestaurant.setCity(restaurant.getCity());
        }

        if(restaurant.getState() != null){
            currRestaurant.setState(restaurant.getState());
        }

        if(restaurant.getTelephone() != null){
            currRestaurant.setTelephone(restaurant.getTelephone());
        }

        if(restaurant.hasvalueforseatcapacity){
            currRestaurant.setSeatcapacity(restaurant.getSeatcapacity());
        }
        //all these fields are a single entity

        //two collections also exist - payments and menus
        //menu
        //OneToMany
        if(restaurant.getMenus().size() > 0){
            currRestaurant.getMenus().clear(); //start with fresh list  -- JUST IN CASE CLEAR IT OUT
            for(Menu m : restaurant.getMenus()) //cycle through list
            {
                Menu newMenu = new Menu(m.getDish(), m.getPrice(), currRestaurant); //dish, price, restaurant  -- we don't care if they send an id (we ignore it)
                currRestaurant.getMenus().add(newMenu);
            }
        }

        //payments -- payments are a little different because they exist on their own
        //ManyToMany
        if(restaurant.getPayments().size() > 0)
        {
            currRestaurant.getPayments().clear();
            for(Payment p : restaurant.getPayments())
            {
                //we don't need to create a new object we need to FIND an object to add
                Payment newPay = payrepos.findById(p.getPaymentid()).orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));//else throw says this is an invalid id
                currRestaurant.getPayments().add(newPay);
            }
        }

        return restrepos.save(currRestaurant); //if you send this with ID of 0 it tries to add // any other ID it replaces
    }





















}
