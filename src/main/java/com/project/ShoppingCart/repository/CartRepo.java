package com.project.ShoppingCart.repository;

import com.project.ShoppingCart.model.Cart;
import com.project.ShoppingCart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
