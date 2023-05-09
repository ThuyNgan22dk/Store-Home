package com.example.demo.repositories;

import com.example.demo.entities.ImportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    @Query(value = "Select * from order_detail where order_id = :order_id",nativeQuery = true)
    List<OrderDetail> getListByOrderId (long order_id);

}

