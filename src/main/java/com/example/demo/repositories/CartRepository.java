package com.example.demo.repositories;

import com.example.demo.entities.Cart;
import com.example.demo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query(value ="Select * from Cart where user_id = :id order by id desc",nativeQuery = true)
    List<Cart> getCartByUser(long id);
}
