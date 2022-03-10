package com.project.ShoppingCart.service;

import com.project.ShoppingCart.dto.CartDto;
import com.project.ShoppingCart.dto.CartItemDto;
import com.project.ShoppingCart.exceptions.CustomException;
import com.project.ShoppingCart.model.Order;
import com.project.ShoppingCart.model.OrderItem;
import com.project.ShoppingCart.model.User;
import com.project.ShoppingCart.repository.OrderItemsRepo;
import com.project.ShoppingCart.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    OrderItemsRepo orderItemsRepo;
    @Autowired
    private CartService cartService;

    public void placeOrder(User user) throws CustomException {
        CartDto cartDto = cartService.listCartItems(user);
        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        if (cartDto.getTotalCost() == 0) {
            throw new CustomException("Cart is empty");
        }
        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDto.getTotalCost());
        orderRepo.save(newOrder);

        for (CartItemDto cartItemDto : cartItemDtoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartItemDto.getProduct().getPrice());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);
            orderItemsRepo.save(orderItem);
        }
        cartService.deleteUserCartItems(user);
    }

    public List<Order> listOrders(User user) {
        return orderRepo.findAllByUserOrderByCreatedDateDesc(user);
    }
}
