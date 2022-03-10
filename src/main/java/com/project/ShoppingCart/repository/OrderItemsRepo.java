package com.project.ShoppingCart.repository;

import com.project.ShoppingCart.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepo extends JpaRepository<OrderItem, Integer> {
}
