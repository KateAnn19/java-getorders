package local.mcgeeka.getorders31.controllers;

import local.mcgeeka.getorders31.models.Order;
import local.mcgeeka.getorders31.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //   PUT /orders/order/{ordernum} - completely replaces the given order
    //    PUT http://localhost:2019/orders/order/63

    //   DELETE /orders/order/{ordernum} - deletes the given order
    //   DELETE http://localhost:2019/orders/order/58
    @DeleteMapping(value = "/order/{ordernum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordernum)
    {
        ordersService.delete(ordernum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
