package local.mcgeeka.getorders31.services;

import local.mcgeeka.getorders31.models.Customer;
import local.mcgeeka.getorders31.repositories.AgentsRepository;
import local.mcgeeka.getorders31.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
