package com.example.demo.services;
import java.util.List;

import com.example.demo.entities.Order;
import com.example.demo.model.request.CreateOrderRequest;

public interface OrderService {

    void placeOrder(CreateOrderRequest request);

    List<Order> getList();

    List<Order> getOrderByUser(String username);
}
