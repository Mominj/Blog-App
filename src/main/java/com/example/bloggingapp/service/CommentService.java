package com.example.bloggingapp.service;

import com.example.bloggingapp.model.Comments;
import com.example.bloggingapp.model.Posts;
import com.example.bloggingapp.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepo commentRepo;

    public void saveComment(Comments comment) {
        commentRepo.save(comment);
    }

    public List<Comments> coommentByPostId(Integer postId) {
       return commentRepo.findByPostId(postId);
    }
}
