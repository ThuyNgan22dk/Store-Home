package com.example.demo.services;
import java.util.List;

import com.example.demo.entities.Cart;
import com.example.demo.entities.Order;
import com.example.demo.entities.OrderDetail;
import com.example.demo.entities.OrderState;
import com.example.demo.model.request.CreateOrderDetailRequest;
import com.example.demo.model.request.CreateOrderRequest;

public interface OrderService {

    void placeOrder(CreateOrderRequest request);

    List<Order> getList();

    List<Order> getOrderByUser(String username);

//    List<OrderDetail> getOrderDetailByIdOrder(Long id);
    void setStateOrder(long orderId, int stateNumber);

    OrderDetail getOrderDetail(Cart cart, OrderDetail orderDetail, CreateOrderDetailRequest rq);
    List<OrderState> getListState(Long orderId);

    List<OrderDetail> getListByOrderId(Long order_id);
}
