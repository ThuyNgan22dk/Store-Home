package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
//    @Query(value ="Select * from Comment where product_id = :id",nativeQuery = true)
//    List<Comment> getListCommentByProduct(long id);
//
//    @Query(value ="Select * from Comment where user_id = :id",nativeQuery = true)
//    List<Comment> getListCommentByUser(long id);

    @Query("Select c from Comment c where c.enable = true")
    List<Comment> findALLByEnabled();
}
