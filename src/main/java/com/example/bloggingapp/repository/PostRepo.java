package com.example.bloggingapp.repository;

import com.example.bloggingapp.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Posts, Integer> {
    @Query(value = "SELECT * FROM posts where is_approved = true" , nativeQuery = true)
    List<Posts> findAllApprovedPosts();

    @Query(value = " select * from posts p where p.user_id= ?1", nativeQuery = true)
    List<Posts> allPostByUserId(Integer userId);

    @Query(value = "SELECT * FROM posts where is_approved = false" , nativeQuery = true)
    List<Posts> findAllNonApprovedPosts();

    @Query(value = "SELECT * FROM posts where id = ?1" , nativeQuery = true)
    Posts finById(Integer id);


//    @Query(value = "DELETE  FROM posts where id = ?1", nativeQuery = true)
//    default boolean deleteBYId(Integer id) {
//        return false;
//    }


}
