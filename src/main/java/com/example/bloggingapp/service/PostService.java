package com.example.bloggingapp.service;


import com.example.bloggingapp.model.Posts;
import com.example.bloggingapp.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepo postRepo;



    @Autowired
    UserService userService;


    public Posts getPostByPostId(Integer postId) {
        return postRepo.finById(postId);
    }


    public void deletePost(Integer id, Principal principal) {
        Posts posts = postRepo.finById(id);
        if(principal.getName() == posts.getUser().getUserName()) {
            postRepo.delete(posts);
        }

    }


}
