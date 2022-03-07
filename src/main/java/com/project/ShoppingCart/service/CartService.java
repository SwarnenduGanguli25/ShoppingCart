package com.project.ShoppingCart.service;

import com.project.ShoppingCart.dto.AddToCartDto;
import com.project.ShoppingCart.model.Cart;
import com.project.ShoppingCart.model.Product;
import com.project.ShoppingCart.model.User;
import com.project.ShoppingCart.repository.CartRepo;
import com.project.ShoppingCart.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartService {
    @Autowired
    ProductService productService;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    ProductRepo productRepo;

    public void addToCart(AddToCartDto addToCartDto, User user) {
        Product product = productService.findById(addToCartDto.getProductId());
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepo.save(cart);
        product.setQuantity(product.getQuantity() - addToCartDto.getQuantity());
        productRepo.save(product);
    }
}
