package com.example.demo.controllers;

import java.util.List;

import com.example.demo.entities.ImportDetail;
import com.example.demo.entities.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Order;
import com.example.demo.model.request.CreateOrderRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*",maxAge = 3600)
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    @Operation(summary="Lấy ra danh sách đặt hàng")
    public ResponseEntity<List<Order>> getList(){
        List<Order> list = orderService.getList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/orderDetail/{order_id}")
    @Operation(summary="Lấy ra danh sách chi tiết các sản phẩm trong đơn mua hàng")
    public ResponseEntity<List<OrderDetail>> getListByOrderId(@PathVariable Long order_id){
        List<OrderDetail> list = orderService.getListByOrderId(order_id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/user")
    @Operation(summary="Lấy ra danh sách đặt hàng của người dùng bằng username")
    public ResponseEntity<List<Order>> getListByUser(@RequestParam("username") String username){
        List<Order> list = orderService.getOrderByUser(username);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/orderDetail")
    public ResponseEntity<List<OrderDetail>> getListDetailNull(){
        return null;
    }

//    @GetMapping("/importDetail/{ig_id}")
//    @Operation(summary="Lấy ra danh sách chi tiết các sản phẩm trong đơn mua hàng")
//    public ResponseEntity<List<OrderDetail>> getListDetail(@PathVariable Long ig_id){
//        List<OrderDetail> list = orderService.getOrderDetail(ig_id);
//        return ResponseEntity.ok(list);
//    }

    @PutMapping("/{id}/{state}")
    @Operation(summary="Đặt hàng sản phẩm")
    public ResponseEntity<?> setStateOrder(@PathVariable("id") Long id, @PathVariable("state") int state){
        orderService.setStateOrder(id,state);
        return ResponseEntity.ok(new MessageResponse("Order Placed Successfully!"));
    }

    @PostMapping("/create")
    @Operation(summary="Đặt hàng sản phẩm")
    public ResponseEntity<?> placeOrder(@RequestBody CreateOrderRequest request){
        orderService.placeOrder(request);
        return ResponseEntity.ok(new MessageResponse("Order Placed Successfully!"));
    }
}

