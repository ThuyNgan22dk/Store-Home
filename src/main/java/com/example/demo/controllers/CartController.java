package com.example.demo.controllers;

import com.example.demo.entities.Cart;
import com.example.demo.model.request.CreateCartRequest;
import com.example.demo.model.request.CreateChangeCartRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*",maxAge = 3600)
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/")
    @Operation(summary="Lấy danh sách các sản phẩm trong giỏ hàng")
    public ResponseEntity<?> getList(){
        List<Cart> carts = cartService.getList();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{username}")
    @Operation(summary="Lấy danh sách các sản phẩm trong giỏ hàng")
    public ResponseEntity<?> getListByUser(@PathVariable String username){
        List<Cart> carts = cartService.getProductByUser(username);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/create")
    @Operation(summary="Tạo mới các sản phẩm trong giỏ hàng")
    public ResponseEntity<Cart> createCart(@RequestBody CreateCartRequest request){
        Cart cart = cartService.createCart(request);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/changeProduct")
    public ResponseEntity<?> changeProductQuantity(@Valid @RequestBody CreateChangeCartRequest request){
        Cart cart = cartService.changeQuantityProductOnCart(request);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update/{id}/{quantity}")
    @Operation(summary="Cập nhật các sản phẩm trong giỏ hàng")
    public ResponseEntity<?> updateCart(@PathVariable("id") long id, @PathVariable("quantity") int quantity){
        Cart cart = cartService.updateCart(id, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/delete/{username}/{cartId}")
    @Operation(summary="Xóa sản phẩm trong giỏ hàng bằng id")
    public ResponseEntity<?> deleteCart(@PathVariable("username") String username, @PathVariable("cartId") long cartId){
        cartService.deleteCart(cartId, username);
        return ResponseEntity.ok(new MessageResponse("Xóa thành công"));
    }
}
