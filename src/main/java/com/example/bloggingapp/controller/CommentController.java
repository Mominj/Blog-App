package com.example.bloggingapp.controller;

import com.example.bloggingapp.model.Comments;
import com.example.bloggingapp.model.Posts;
import com.example.bloggingapp.model.Users;
import com.example.bloggingapp.repository.UsersRepo;
import com.example.bloggingapp.service.CommentService;
import com.example.bloggingapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;



    @PostMapping("/create_comment/{post_id}")
    public String createComment(@PathVariable("post_id") Integer postId,
                                Comments comment,
                                Principal principal){

        Posts post = postService.getPostByPostId(postId);
        Users user = usersRepo.findUsersByUserName(principal.getName());
        comment.setPosts(post);
        comment.setUsers(user);
        comment.setCreationDate(new Date());
        commentService.saveComment(comment);
        return "redirect:/view/post/" + comment.getPosts().getId();

    }


}
