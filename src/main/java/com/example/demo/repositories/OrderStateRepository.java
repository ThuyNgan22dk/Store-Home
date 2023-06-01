package com.example.demo.repositories;

import com.example.demo.entities.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderState,Long>{
    @Query(value = "Select * from order_state where order_id = :order_id",nativeQuery = true)
    List<OrderState> getListByOrderId (long order_id);

    @Query(value = "Select * from order_state where order_id = :order_id and state = :state",nativeQuery = true)
    OrderState checkState (long order_id, String state);
}
