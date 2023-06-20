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

    @Query(value = "SELECT * from Cart where product_id = :productId and user_id = :userId", nativeQuery = true)
    List<Cart> findProductOnCart(long productId, long userId);

    @Query(value = "SELECT * from Cart where id = :cartId and user_id = :userId", nativeQuery = true)
    Cart findProductByIdAndUser(long cartId, long userId);
}
