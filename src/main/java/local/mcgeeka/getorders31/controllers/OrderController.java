package local.mcgeeka.getorders31.controllers;

import local.mcgeeka.getorders31.models.Customer;
import local.mcgeeka.getorders31.models.Order;
import local.mcgeeka.getorders31.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

//GET /orders/order/{id} - Returns the order and its customer with the given order number
// GET /customers/orders/count - Using a custom query, return a list of all customers with the number of orders they have placed.

@RestController
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    private OrderService ordersService;


    //    http://localhost:2019/orders/order/7
    //    GET /orders/order/{id} - Returns the order and its customer with the given order number
    @GetMapping(value = "/order/{id}", produces = {"application/json"})
    public ResponseEntity<?> findOrderById(@PathVariable long id){
        Order o = ordersService.findByOrderNum(id);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }


    //   POST /orders/order - adds a new order to an existing customer
    //    POST http://localhost:2019/orders/order
    @PostMapping(value = "/order", consumes = {"application/json"})
    public ResponseEntity<?> addNewRestaurant(@Valid @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0); //this wipes out an id if the client sends a new object with an id (we don't want that)
        //we already have a save method so we use it to save
        newOrder = ordersService.save(newOrder);
        // in order to tell client how to access the newly created object
        //location header sent back -- this below creates a location header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRestaurantURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{ordnum}/").buildAndExpand(newOrder.getOrdnum()).toUri();
        responseHeaders.setLocation(newRestaurantURI);   //this takes in a URI (required)
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
        //could return the new JSON object instead of null if you wanted to
    }

    //   PUT /orders/order/{ordernum} - completely replaces the given order
    //    PUT http://localhost:2019/orders/order/63
    @PutMapping(value = "/order/{id}", consumes = "application/json")
    public ResponseEntity<?> updateOrder(@PathVariable long id, @Valid @RequestBody Order updateOrder) // has two parameters and order doesn't matter
    {
        updateOrder.setOrdnum(id);
        ordersService.save(updateOrder);
        return new ResponseEntity<>(updateOrder,HttpStatus.OK);
    }

    //   DELETE /orders/order/{ordernum} - deletes the given order
    //   DELETE http://localhost:2019/orders/order/58
    @DeleteMapping(value = "/order/{ordernum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordernum)
    {
        ordersService.delete(ordernum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
