package local.mcgeeka.getorders31.controllers;

import local.mcgeeka.getorders31.models.Order;
import local.mcgeeka.getorders31.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> findOrderById(@PathVariable
                                               long id){
        Order o = ordersService.findByOrderNum(id);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
}
