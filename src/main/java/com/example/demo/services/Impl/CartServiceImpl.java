package com.example.demo.services.Impl;

import com.example.demo.entities.Cart;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.CreateCartRequest;
import com.example.demo.model.request.CreateChangeCartRequest;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        Product product = productRepository.findByProductname(rq.getName()).orElseThrow(() -> new NotFoundException("Not Found User With Product:" + rq.getName()));
        if (product.getQuantity() - rq.getQuantity() >= 0) {
            User user = userRepository.findByUsername(rq.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + rq.getUsername()));
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setName(rq.getName());
            cart.setProduct(product);

            cart.setPrice(product.getPrice());
            cart.setExpiry(product.getExpiry());
            cart.setQuantity(rq.getQuantity());
            cart.setTotal(cart.getPrice() * cart.getQuantity());
            System.out.println(cart.getName());
            cartRepository.save(cart);
            return cart;
        }
        return null;
    }

    @Override
    public Cart changeQuantityProductOnCart(CreateChangeCartRequest rq){
        User user = userRepository.findByUsername(rq.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + rq.getUsername()));
        Product product = productRepository.findByProductname(rq.getName()).orElseThrow(() -> new NotFoundException("Not Found User With Product:" + rq.getName()));
        Cart cart = cartRepository.findProductOnCart(product.getId(), user.getId());
        if ((rq.getQuantity() + rq.getQuantityAdd()) <= product.getQuantity()) {
            cart.setQuantity(rq.getQuantity() + rq.getQuantityAdd());
            cart.setTotal(cart.getPrice() * cart.getQuantity());
            System.out.println(cart.getQuantity());
//            cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public Cart updateCart(long id, int quantity) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Cart With Id: " + id));
<<<<<<< Updated upstream
        cart.setQuantity(quantity);
        cartRepository.save(cart);
=======
        if (quantity <= cart.getProduct().getQuantity()) {
            cart.setQuantity(quantity);
            cart.setTotal(cart.getPrice() * cart.getQuantity());
            cartRepository.save(cart);
        }
        System.out.println("Khong the cap nhat");
>>>>>>> Stashed changes
        return cart;
    }

    @Override
    public void deleteCart(long cartId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User With username: " + username));
        Cart cart = cartRepository.findProductByIdAndUser(cartId, user.getId());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        cart.setDateDeleted(dtf.format(now));
        cartRepository.save(cart);
    }

    @Override
    public List<Cart> getList() {
        List<Cart> list = cartRepository.findAll(Sort.by("id").descending());
        List<Cart> carts = new ArrayList<>();
        for (Cart cart: list){
            if (cart.getDateDeleted() == null) {
                carts.add(cart);
//                System.out.println(cart);
            }
        }
        return carts;
    }

    @Override
    public List<Cart> getProductByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + username));
        List<Cart> list = cartRepository.getCartByUser(user.getId());
        List<Cart> carts = new ArrayList<>();
        for (Cart cart: list){
            if (cart.getDateDeleted() == null) {
                carts.add(cart);
//                System.out.println(cart);
            }
        }
        return carts;
    }
}
