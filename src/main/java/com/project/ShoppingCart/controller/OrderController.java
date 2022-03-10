package com.project.ShoppingCart.controller;

import com.project.ShoppingCart.common.ApiResponse;
import com.project.ShoppingCart.exceptions.CustomException;
import com.project.ShoppingCart.model.Order;
import com.project.ShoppingCart.model.User;
import com.project.ShoppingCart.service.OrderService;
import com.project.ShoppingCart.service.ProductService;
import com.project.ShoppingCart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("userId") int userId) throws CustomException {
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new CustomException("User id is invalid");
        }
        User user = optionalUser.get();
        orderService.placeOrder(user);

        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("userId") int userId) throws CustomException {
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new CustomException("User id is invalid");
        }
        User user = optionalUser.get();
        List<Order> orderDtoList = orderService.listOrders(user);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }
}
