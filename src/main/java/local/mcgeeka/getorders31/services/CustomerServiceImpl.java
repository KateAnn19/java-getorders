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
}
