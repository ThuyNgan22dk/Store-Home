package com.example.demo.services;

import com.example.demo.entities.Cart;
import com.example.demo.model.request.CreateCartRequest;

import java.util.List;

public interface CartService {
    Cart createCart(CreateCartRequest request);

//    Cart addCart(long id, int quantity, String username);

    Cart updateCart(long id, int quantity);

    void deleteCart(long id);
    List<Cart> getList();

    List<Cart> getProductByUser(String username);
}
