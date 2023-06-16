package com.example.demo.services.Impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private OrderStateRepository orderStateRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WarehouseServise warehouseServise;

    @Autowired
    private PromotionService promotionService;

    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public LocalDateTime now = LocalDateTime.now();

    @Override
    public void placeOrder(CreateOrderRequest request) {
        // TODO Auto-generated method stub
        Order order = new Order();
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + request.getUsername()));
        order.setEmail(user.getEmail());
        if(request.getFirstname() == null){
            order.setFirstname(user.getFirstname());
        } else {
            order.setFirstname(request.getFirstname());
            user.setFirstname(request.getFirstname());
        }
        if(request.getLastname() == null){
            order.setLastname(user.getLastname());
        } else {
            order.setLastname(request.getLastname());
            user.setLastname(request.getLastname());
        }
        if(request.getPhone() == null){
            order.setPhone(user.getPhone());
        } else {
            order.setPhone(request.getPhone());
            user.setPhone(request.getPhone());
        }
        order.setAddress(request.getAddress());
        user.setAddress(request.getAddress());
        user.setCountry(request.getCountry());
        order.setNote(request.getNote());
        order.setDateCreated(dtf.format(now));
        order.setDateTime(dtf2.format(now));
        orderRepository.save(order);
        long totalPrice = 0;
        for(CreateOrderDetailRequest rq: request.getOrderDetails()){
            Cart cart = cartRepository.findById(rq.getCartId()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + rq.getCartId()));
            ChangeWarehouseRequest changeWarehouseRequest = new ChangeWarehouseRequest(cart.getName(), cart.getQuantity(), cart.getExpiry(), "");
            if (warehouseServise.addOrder(changeWarehouseRequest)){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail = getOrderDetail(cart, orderDetail, rq);
                orderDetail.setCart(cart);
                orderDetail.setOrder(order);
                totalPrice += orderDetail.getSubTotal();
                orderDetailRepository.save(orderDetail);
                cart.setDateDeleted(dtf.format(now));
                cartRepository.save(cart);
            }
        }
        if (request.getPromotionCode() != null) {
            Promotion promotion = promotionService.findCode(request.getPromotionCode());
            order.setPromotion(promotion);
            System.out.println(promotion);
            order.setTotalPrice(totalPrice * (100 - promotion.getPercent()) / 100);
            if (promotion.getQuantity() == 1) {
                promotionService.enablePromotion(promotion.getId());
            } else if (promotion.getQuantity() > 1) {
                promotion.setQuantity(promotion.getQuantity() - 1);
            }
        } else {
            order.setTotalPrice(totalPrice);
        }
        setStateOrder(order.getId(), 1);
        order.setUser(user);
        orderRepository.save(order);
    }

    @Override
    public List<Order> getList() {
        List<Order> orderList = orderRepository.findAll();
        List<Order> list = new ArrayList<>();
        for(Order order: orderList){
            if (order.getDateDeleted() == null) {
                list.add(order);
            }
        }
        return list;
    }

    @Override
    public long totalAllOrder(){
        List<Order> listOrder = orderRepository.findAll();
        long totalOrder = 0;
        for (Order order : listOrder) {
            if (order.getDateDeleted() == null) {
                totalOrder += order.getTotalPrice();
            }
        }
        return totalOrder;
    }

    @Override
    public long getOrderForChart(String date){
        List<Order> listOrder = orderRepository.getOrderDay(date);
        long totalOrder = 0;
        for (Order order : listOrder) {
            if (order.getDateDeleted() == null) {
                totalOrder += order.getTotalPrice();
            }
        }
        return totalOrder;
    }

    @Override
    public List<Order> getOrderByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + username));
        return orderRepository.getOrderByUser(user.getId());
    }

    @Override
    public void setStateOrder(long orderId, int stateNumber) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + orderId));
        OrderState orderState = new OrderState();
//        System.out.println(orderStateRepository.checkState(orderId, "Đơn đã đặt"));
        switch (stateNumber){
            case 1: {
                if (orderStateRepository.checkState(orderId, "Đơn đã đặt") == null){
                    orderState.setState("Đơn đã đặt");
                }
                break;
            }
            case 2: {
                if (orderStateRepository.checkState(orderId, "Đang chuẩn bị") == null){
                    orderState.setState("Đang chuẩn bị");
                }
                break;
            }
            case 3: {
                if (orderStateRepository.checkState(orderId, "Đang giao hàng") == null){
                    orderState.setState("Đang giao hàng");
                }
                break;
            }
            case 4: {
                if (orderStateRepository.checkState(orderId, "Giao hàng thành công") == null){
                    orderState.setState("Giao hàng thành công");
                }
                break;
            }
            default: {
                if (orderStateRepository.checkState(orderId, "Không thành công") == null){
                    orderState.setState("Không thành công");
                }
                break;
            }
        }
        orderState.setDatetime(dtf.format(now));
        orderState.setOrder(order);
        orderStateRepository.save(orderState);
        order.setStating(orderState.getState());
        orderRepository.save(order);
//        return orderState;
    }

    @Override
    public List<OrderDetail> getListByOrderId(Long orderId){
        return orderDetailRepository.getListByOrderId(orderId);
    }

    @Override
    public List<OrderState> getListState(Long orderId){
        return orderStateRepository.getListByOrderId(orderId);
    }

    @Override
    public OrderDetail getOrderDetail(Cart cart,OrderDetail orderDetail, CreateOrderDetailRequest rq){
        orderDetail.setName(cart.getName());
        orderDetail.setPrice(cart.getPrice());
        orderDetail.setExpiry(cart.getExpiry());
        orderDetail.setQuantity(cart.getQuantity());
        orderDetail.setSubTotal(cart.getPrice()* cart.getQuantity());
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }

}

