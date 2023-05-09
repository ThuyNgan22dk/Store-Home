package com.example.demo.services.Impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.demo.entities.*;
import com.example.demo.model.request.ChangeWarehouseRequest;
import com.example.demo.repositories.*;
import com.example.demo.services.PromotionService;
import com.example.demo.services.WarehouseServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.CreateOrderDetailRequest;
import com.example.demo.model.request.CreateOrderRequest;
import com.example.demo.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WarehouseServise warehouseServise;

    @Autowired
    private PromotionService promotionService;

    @Override
    public void placeOrder(CreateOrderRequest request) {
        // TODO Auto-generated method stub
        Order order = new Order();
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + request.getUsername()));
        order.setFirstname(user.getFirstname());
        order.setLastname(user.getLastname());
        order.setEmail(user.getEmail());
        order.setPhone(user.getPhone());
        order.setAddress(request.getAddress());
        order.setNote(request.getNote());
        //Date-time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        order.setDateCreated(dtf.format(now));

        orderRepository.save(order);
        long totalPrice = 0;
        for(CreateOrderDetailRequest rq: request.getOrderDetails()){
            Cart cart = cartRepository.findById(rq.getCartId()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + rq.getCartId()));
            ChangeWarehouseRequest changeWarehouseRequest = new ChangeWarehouseRequest(cart.getName(), cart.getQuantity(), cart.getExpiry());
            if (warehouseServise.addOrder(changeWarehouseRequest)){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail = getOrderDetail(cart, orderDetail, rq);
                orderDetail.setOrder(order);
                totalPrice += orderDetail.getSubTotal();
                orderDetailRepository.save(orderDetail);
            }
        }
        order.setTotalPrice(totalPrice);
        order.setUser(user);
        orderRepository.save(order);
    }

    @Override
    public List<Order> getList() {
        return orderRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public List<Order> getOrderByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + username));
        return orderRepository.getOrderByUser(user.getId());
    }

    @Override
    public void setStateOrder(Long id, int stateOrder) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + id));
        switch (stateOrder){
            case 1: order.setState("Đơn đã đặt");
            case 2: order.setState("Đang chuẩn bị");
            case 3: order.setState("Đang giao hàng");
            case 4: order.setState("Được đánh giá");
            default: order.setState("Không thành công");
        }
        orderRepository.save(order);
    }

    @Override
    public List<OrderDetail> getListByOrderId(Long order_id){
        return orderDetailRepository.getListByOrderId(order_id);
    }

    @Override
    public OrderDetail getOrderDetail(Cart cart,OrderDetail orderDetail, CreateOrderDetailRequest rq){
        String promotionCode = rq.getPromotionCode();
        if (promotionCode != null) {
            Promotion promotion = promotionService.findCode(rq.getPromotionCode());
            System.out.println(promotion);
            orderDetail.setSubTotal(cart.getPrice() * cart.getQuantity() * promotion.getPercent());
            promotion.setQuantity(promotion.getQuantity() - 1);
        }else {
            orderDetail.setSubTotal(cart.getPrice() * cart.getQuantity());
        }
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }

}

