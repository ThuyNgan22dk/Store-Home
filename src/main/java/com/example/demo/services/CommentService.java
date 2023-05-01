package com.example.demo.services;
import java.util.List;

import com.example.demo.entities.Comment;
import com.example.demo.model.request.CreateCommentRequest;

public interface CommentService {
    List<Comment> findAll();

    List<Comment> getListEnabled();

    Comment createComment(CreateCommentRequest request);

    Comment updateComment(long id,CreateCommentRequest request);

    void enableComment(long id);

    void deleteComment(long id);
}
