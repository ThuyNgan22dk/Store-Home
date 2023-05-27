package com.example.demo.services.Impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.entities.*;
import com.example.demo.model.request.ChangeWarehouseRequest;
import com.example.demo.model.request.CreateOrderStateRequest;
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

    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    public LocalDateTime now = LocalDateTime.now();

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
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
        order.setDateCreated(dtf.format(now));

        orderRepository.save(order);
        long totalPrice = 0;
        for(CreateOrderDetailRequest rq: request.getOrderDetails()){
            Cart cart = cartRepository.findById(rq.getCartId()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + rq.getCartId()));
            ChangeWarehouseRequest changeWarehouseRequest = new ChangeWarehouseRequest(cart.getName(), cart.getQuantity(), cart.getExpiry(), "");
            if (warehouseServise.addOrder(changeWarehouseRequest)){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail = getOrderDetail(cart, orderDetail, rq);
                orderDetail.setOrder(order);
                totalPrice += orderDetail.getSubTotal();
                orderDetailRepository.save(orderDetail);
<<<<<<< Updated upstream
=======
                cart.setDateDeleted(dtf.format(now));
                cartRepository.save(cart);
            } else {
//                System.out.println("Khong the dat hang");
>>>>>>> Stashed changes
            }
        }
        if (request.getPromotionCode() != null) {
            Promotion promotion = promotionService.findCode(request.getPromotionCode());
            order.setPromotionCode(request.getPromotionCode());
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
//        Set<OrderState> orderStates = new HashSet<>();
//        orderStates.add(setStateOrder(order.getId(), 1));
//        order.setStating(setStateOrder(order.getId(), 1).getState());
        setStateOrder(order.getId(), 1);
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
    public void setStateOrder(long orderId, int stateNumber) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + orderId));
        OrderState orderState = new OrderState();
        switch (stateNumber){
            case 1: orderState.setState("Đơn đã đặt");
            case 2: orderState.setState("Đang chuẩn bị");
            case 3: orderState.setState("Đang giao hàng");
            case 4: orderState.setState("Giao hàng thành công");
            default: orderState.setState("Không thành công");
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

<<<<<<< Updated upstream
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
=======
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
>>>>>>> Stashed changes
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }

}

