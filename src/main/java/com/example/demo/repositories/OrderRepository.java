package com.example.demo.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query(value ="Select * from Orders where user_id = :id order by id desc",nativeQuery = true)
    List<Order> getOrderByUser(long id);

    @Query(value ="Select * from Orders where date_time = :dateTime",nativeQuery = true)
    List<Order> getOrderDay(String dateTime);
}
