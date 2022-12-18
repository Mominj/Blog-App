package com.example.bloggingapp.repository;

import com.example.bloggingapp.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comments, Integer> {

    @Query(value = "select * from comments where post_id = ?1 ",nativeQuery = true)
    List<Comments> findByPostId(Integer postId);
}
