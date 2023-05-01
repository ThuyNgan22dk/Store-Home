package com.example.demo.services.Impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.demo.entities.Promotion;
import com.example.demo.model.request.ChangeWarehouseRequest;
import com.example.demo.repositories.PromotionRepository;
import com.example.demo.services.PromotionService;
import com.example.demo.services.WarehouseServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Order;
import com.example.demo.entities.OrderDetail;
import com.example.demo.entities.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.CreateOrderDetailRequest;
import com.example.demo.model.request.CreateOrderRequest;
import com.example.demo.repositories.OrderDetailRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

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
//        order.setPromotionCode(user.getPromotionCode());
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
            ChangeWarehouseRequest changeWarehouseRequest = new ChangeWarehouseRequest(rq.getProductname(), rq.getQuantity(), rq.getExpiry());
            if (warehouseServise.addOrder(changeWarehouseRequest)){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setName(rq.getProductname());
                orderDetail.setPrice(rq.getPrice());
                orderDetail.setQuantity(rq.getQuantity());
                Promotion promotion = promotionService.findCode(rq.getPromotionCode());
                System.out.println(promotion);
                if (promotion != null) {
                    orderDetail.setSubTotal(rq.getPrice() * rq.getQuantity() * promotion.getPercent());
                    promotion.setQuantity(promotion.getQuantity() - 1);
                }else {
                    orderDetail.setSubTotal(rq.getPrice() * rq.getQuantity());
                }
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

}

