package com.example.demo.services.Impl;

import com.example.demo.entities.Cart;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.CreateCartRequest;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Cart createCart(CreateCartRequest rq) {
        Cart cart = new Cart();
        User user = userRepository.findByUsername(rq.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + rq.getUsername()));
        cart.setUser(user);
        cart.setName(rq.getName());
        Product product = productRepository.findByProductname(rq.getName()).orElseThrow(() -> new NotFoundException("Not Found User With Product:" + rq.getName()));
        cart.setProduct(product);
        cart.setPrice(product.getPrice());
        cart.setExpiry(product.getExpiry());
        cart.setQuantity(rq.getQuantity());
        cart.setTotal(cart.getPrice() * cart.getQuantity());
        System.out.println(cart.getName());
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart updateCart(long id, int quantity) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Cart With Id: " + id));
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public void deleteCart(long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Cart With Id: " + id));
        cartRepository.delete(cart);
    }

    @Override
    public List<Cart> getList() {
        return cartRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public List<Cart> getProductByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + username));
        return cartRepository.getCartByUser(user.getId());
    }
}
