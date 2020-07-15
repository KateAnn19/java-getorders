package local.mcgeeka.getorders31.services;

import local.mcgeeka.getorders31.models.Customer;
import local.mcgeeka.getorders31.views.CustomerOrder;

import java.util.List;

public interface CustomerService
{
    Customer save(Customer customer);

    Customer findByCustomerCode(long id);
    List<Customer> findByNameLike(String thename);
    List <Customer> findAllCustomers();
    List<CustomerOrder> getOrderCount();
}
