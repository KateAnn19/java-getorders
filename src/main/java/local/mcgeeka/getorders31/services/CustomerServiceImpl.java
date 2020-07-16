package local.mcgeeka.getorders31.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import local.mcgeeka.getorders31.models.Agent;
import local.mcgeeka.getorders31.models.Customer;
import local.mcgeeka.getorders31.models.Order;
import local.mcgeeka.getorders31.repositories.AgentsRepository;
import local.mcgeeka.getorders31.repositories.CustomersRepository;
import local.mcgeeka.getorders31.views.CustomerOrder;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService
{
    @Autowired
    private CustomersRepository custrepos;

    //this save method is now below - we added to it

    //    @Override
    //    public Customer save(Customer customer)
    //    {
    //        return custrepos.save(customer);
    //    }

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
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAgentfoo(customer.getAgentfoo());
        //all these fields are a single entity
        /*@ManyToOne
        @JoinColumn(name = "agentcode", nullable = false) //this connects agent to customer//nullable forces customers to have an agent
        @JsonIgnoreProperties("customersfoo")
        private Agent agentfoo;
        */
        //two collections also exist - payments and menus
        //menu
        //OneToMany
        newCustomer.getOrdersfoo().clear(); //start with fresh list  -- JUST IN CASE CLEAR IT OUT
        for(Order o : customer.getOrdersfoo()) //cycle through list
        {
            Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), newCustomer, o.getOrderdescription());
            newCustomer.getOrdersfoo().add(newOrder);
        }
        return custrepos.save(newCustomer); //if you send this with ID of 0 it tries to add // any other ID it replaces
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long custcode)
    {

        Customer currCustomer = custrepos.findById(custcode).orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " not found!"));
        //all these fields are a single entity
        if(customer.getCustname() != null)
        {
            currCustomer.setCustname(customer.getCustname());
        }

        if(customer.getCustcity() !=  null)
        {
            currCustomer.setCustcity(customer.getCustcity());
        }

        if(customer.getWorkingarea() != null)
        {
            currCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if(customer.getCustcountry() != null){
            currCustomer.setCustcountry(customer.getCustcountry());
        }

        if(customer.getGrade() != null){
            currCustomer.setGrade(customer.getGrade());
        }

        if(customer.hasvalueforopeningamt){
            currCustomer.setOpeningamt(customer.getOpeningamt());
        }

        if(customer.hasvalueforreceiveamt){
            currCustomer.setReceiveamt(customer.getReceiveamt());
        }

        if(customer.hasvalueforpaymentamt){
            currCustomer.setPaymentamt(customer.getPaymentamt());
        }

        if(customer.hasvalueforoutstandingamt){
            currCustomer.setOutstandingamt(customer.getOutstandingamt());
        }

        if(customer.getPhone() != null){
            currCustomer.setPhone(customer.getPhone());
        }

        if(customer.getAgentfoo() != null){
            currCustomer.setAgentfoo(customer.getAgentfoo());
        }
        //all these fields are a single entity

        //collections
        //OneToMany
        if(customer.getOrdersfoo().size() > 0){
            currCustomer.getOrdersfoo().clear(); //start with fresh list  -- JUST IN CASE CLEAR IT OUT
            for(Order o : customer.getOrdersfoo()) //cycle through list
            {
                Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), currCustomer, o.getOrderdescription()); //dish, price, restaurant  -- we don't care if they send an id (we ignore it)
                currCustomer.getOrdersfoo().add(newOrder);
            }
        }
        return custrepos.save(currCustomer); //if you send this with ID of 0 it tries to add // any other ID it replaces
    }





















}
