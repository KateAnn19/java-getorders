package local.mcgeeka.getorders31.controllers;

import local.mcgeeka.getorders31.models.Customer;
import local.mcgeeka.getorders31.services.CustomerService;
import local.mcgeeka.getorders31.views.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<?> findCustomerById(@PathVariable long id){
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


    //    POST /customers/customer - Adds a new customer including any new orders
    //    POST http://localhost:2019/customers/customer

    //Create - POST
    //Request Body - JSON Object New Restaurant -- in body will be new data to send over
    //this is the most complicated thing inside the controller
    //we create a new record in database
    //an ID will be assigned
    //We have to let client know how to find the new record (by id)
    //we're bringing in a new JSON oject with this
    //this actually consumes JSON -- so we use "consumes"
    //you could list multiple types it can consume ... so you might see it in {} (a list)
    //doesn't need @PathVariable with a POST ... needs @RequestBody
    //Jackson converts data into an object
    //@Valid is used to check that the incoming data is valid and throws and error if it isn't
    //@Valid is not bulletproof but it's good
    //CLIENT IS SENDING INFO BUT NOT SENDING ID
    @PostMapping(value = "/customer", consumes = {"application/json"})
    public ResponseEntity<?> addNewRestaurant(@Valid @RequestBody Customer newCust)
    {
        newCust.setCustcode(0); //this wipes out an id if the client sends a new object with an id (we don't want that)
        //we already have a save method so we use it to save
        newCust = customerService.save(newCust);
        // in order to tell client how to access the newly created object
        //location header sent back -- this below creates a location header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRestaurantURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{custcode}/").buildAndExpand(newCust.getCustcode()).toUri();
        responseHeaders.setLocation(newRestaurantURI);   //this takes in a URI (required)
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
        //could return the new JSON object instead of null if you wanted to
    }

    //    PUT /customers/customer/{custcode} - completely replaces the customer record including associated orders with the provided data
    //    PUT http://localhost:2019/customers/customer/19

    //PUT
    //we'll be changing the menu id's (though with a PUT you're really not 'supposed' to change any of the data
    //We send it a Request Body and it must be complete because it's a complete replacement - JSON Object
    @PutMapping(value = "/restaurant/{id}", consumes = "application/json")
    public ResponseEntity<?> updateRestaurant(@PathVariable long id, @Valid @RequestBody Restaurant updateRest) // has two parameters and order doesn't matter
    {
        updateRest.setRestaurantid(id);
        restaurantServices.save(updateRest);
        return new ResponseEntity<>(updateRest,HttpStatus.OK);
    }

    //    PATCH /customers/customer/{custcode} - updates customers with the new data. Only the new data is to be sent from the frontend client.
    //    PATCH http://localhost:2019/customers/customer/19

    //PATCH
    //brings in request body so needs consumes
    @PatchMapping(value = "/restaurant/{restid}", consumes = "application/json")
    public ResponseEntity<?> updatePartRestaurant(@RequestBody Restaurant updateRestaurant, @PathVariable long restid)
    {
        restaurantServices.update(updateRestaurant, restid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //    DELETE /customers/customer/{custcode} - Deletes the given customer including any associated orders
        //    DELETE http://localhost:2019/customers/customer/54
        @DeleteMapping(value = "/customer/{custcode}")
        public ResponseEntity<?> deleteCustById(@PathVariable long custcode)
        {
             customerService.delete(custcode);
             return new ResponseEntity<>(HttpStatus.OK);
        }

}

