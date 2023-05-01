package com.example.demo.services.Impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entities.Category;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.model.request.CreateCommentRequest;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.exception.NotFoundException;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Comment> findAll() {
        // TODO Auto-generated method stub
        List<Comment> commentList = commentRepository.findAll(Sort.by("id").descending());
        List<Comment> list = new ArrayList<>();
        for(Comment comment: commentList){
            if (comment.getDateDeleted() == null) {
                list.add(comment);
            }
        }
        return list;
    }

    @Override
    public Comment createComment(CreateCommentRequest request) {
        // TODO Auto-generated method stub
        Comment comment = new Comment();
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + request.getUserId()));
        comment.setUser(user);
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new NotFoundException("Not Found User With Product name:" + request.getProductId()));
        comment.setProduct(product);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        comment.setDateCreated(dtf.format(now));
        comment.setRate(request.getRate());
        comment.setEnable(true);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Comment updateComment(long id, CreateCommentRequest request) {
        // TODO Auto-generated method stub
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + id));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + request.getUserId()));
        comment.setUser(user);
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new NotFoundException("Not Found User With Product name:" + request.getProductId()));
        comment.setProduct(product);
        comment.setRate(request.getRate());
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public void enableComment(long id) {
        // TODO Auto-generated method stub
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + id));
        comment.setEnable(!comment.isEnable());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long id) {
        // TODO Auto-generated method stub
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + id));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        comment.setDateDeleted(dtf.format(now));
        comment.setEnable(false);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getListEnabled() {
        // TODO Auto-generated method stub
        return commentRepository.findALLByEnabled();
    }
}

