package local.mcgeeka.getorders31.controllers;

import local.mcgeeka.getorders31.models.Customer;
import local.mcgeeka.getorders31.services.CustomerService;
import local.mcgeeka.getorders31.views.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController
{
    @Autowired
    private CustomerService customerService;


    //    http://localhost:2019/customers/orders
    //    GET /customers/orders - Returns all customers with their orders
    @GetMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<?> listAllCustomers(){
        List<Customer> myCustomers = customerService.findAllCustomers();
        return new ResponseEntity<>(myCustomers, HttpStatus.OK);
    }

    //    http://localhost:2019/customers/customers/7
    //    GET /customers/customer/{id} - Returns the customer and their orders with the given customer id
    @GetMapping(value = "/customers/{id}", produces = {"application/json"})
    public ResponseEntity<?> findRestaurantById(@PathVariable
                                                    long id){
        Customer c = customerService.findByCustomerCode(id);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    //    GET /customers/namelike/{likename} -
    //    Returns all customers and their orders with a customer name containing the given substring
    // http://localhost:2019/customers/namelike/mes
    //GET /customers/namelike/{likename} - Returns all customers and their orders with a customer name containing the given substring
    @GetMapping(value = "/customers/namelike/{thename}", produces = {"application/json"})
    public ResponseEntity<?> listAllCustomersLikeName(@PathVariable String thename){
        List<Customer> myCustomers = customerService.findByNameLike(thename);
        return new ResponseEntity<>(myCustomers, HttpStatus.OK);
    }


    //GET /customers/orders/count - Using a custom query,
    // return a list of all customers with the number of orders they have placed.
    @GetMapping(value = "/orders/count", produces = {"application/json"})
    public ResponseEntity<?> findOrderCounts(){
        //name of customer and count
        List<CustomerOrder> myList = customerService.getOrderCount();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }


}

