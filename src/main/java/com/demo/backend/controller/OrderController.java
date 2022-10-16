package com.demo.backend.controller;

import com.demo.backend.dto.ApiResponseDto;
import com.demo.backend.dto.CartDto;
import com.demo.backend.model.Order;
import com.demo.backend.model.User;
import com.demo.backend.service.OrderService;
import com.demo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    // place order after checkout
    @PostMapping("/checkout")
    public ResponseEntity<ApiResponseDto> placeOrder(@RequestParam("userId") int userId) {

        User user = userService.getUserById(userId);
        // place the order
        orderService.placeOrder(user);
        return new ResponseEntity<>(new ApiResponseDto(true, "Order has been placed"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getOrders(@RequestParam("userId") int userId) {
        User user = userService.getUserById(userId);
        List<Order> orderList= orderService.getOrdersByUser(user);
        return new ResponseEntity<List<Order>>(orderList,HttpStatus.OK);
    }

}
