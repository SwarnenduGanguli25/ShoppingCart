package com.project.ShoppingCart.service;

import com.project.ShoppingCart.dto.AddToCartDto;
import com.project.ShoppingCart.dto.CartDto;
import com.project.ShoppingCart.dto.CartItemDto;
import com.project.ShoppingCart.model.Cart;
import com.project.ShoppingCart.model.Product;
import com.project.ShoppingCart.model.User;
import com.project.ShoppingCart.repository.CartRepo;
import com.project.ShoppingCart.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public CartDto listCartItems(User user) {
        final List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }
        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);

        return cartDto;
    }
}