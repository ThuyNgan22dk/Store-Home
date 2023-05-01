package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.entities.Comment;
import com.example.demo.model.request.CreateCommentRequest;
import com.example.demo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.response.MessageResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = "*",maxAge = 3600)
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    @Operation(summary="Lấy danh sách bình luận")
    public ResponseEntity<?> getListComment(){
        List<Comment> categories = commentService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/enabled")
    @Operation(summary="Lấy ra danh sách bình luận đã kích hoạt")
    public ResponseEntity<List<Comment>> getListEnabled(){
        List<Comment> comments = commentService.getListEnabled();
        return ResponseEntity.ok(comments);
    }


    @PostMapping("/create")
    @Operation(summary="Tạo mới bình luận")
    public ResponseEntity<?> createComment(@Valid @RequestBody CreateCommentRequest request){
        Comment comment = commentService.createComment(request);

        return ResponseEntity.ok(comment);
    }

    @PutMapping("/update/{id}")
    @Operation(summary="Tìm bình luận bằng id và cập nhật bình luận đó")
    public ResponseEntity<?> updateComment(@PathVariable long id, @Valid @RequestBody CreateCommentRequest request){
        Comment comment = commentService.updateComment(id, request);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/enable/{id}")
    @Operation(summary="Kích hoạt bình luận bằng id")
    public ResponseEntity<?> enabled(@PathVariable long id){
        commentService.enableComment(id);
        return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa bình luận bằng id")
    public ResponseEntity<?> delete(@PathVariable long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok(new MessageResponse("Xóa thành công"));
    }

}

