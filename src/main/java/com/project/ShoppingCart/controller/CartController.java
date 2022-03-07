package com.project.ShoppingCart.controller;

import com.project.ShoppingCart.common.ApiResponse;
import com.project.ShoppingCart.dto.AddToCartDto;
import com.project.ShoppingCart.model.User;
import com.project.ShoppingCart.service.CartService;
import com.project.ShoppingCart.service.ProductService;
import com.project.ShoppingCart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto) {
        Optional<User> optionalUser = userService.findById(addToCartDto.getUserId());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid user id : " + addToCartDto.getUserId()), HttpStatus.CONFLICT);
        }
        if (productService.outOfStock(addToCartDto)) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "This product is out of stock"), HttpStatus.CONFLICT);
        }
        if (productService.checkStockAvailability(addToCartDto)) {
            int availableStock = productService.getAvailableStock(addToCartDto);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Only " + availableStock + " available in stock"), HttpStatus.CONFLICT);
        }
        User user = optionalUser.get();
        cartService.addToCart(addToCartDto, user);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }
}
