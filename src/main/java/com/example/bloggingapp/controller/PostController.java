package com.example.bloggingapp.controller;


import com.example.bloggingapp.model.Comments;
import com.example.bloggingapp.model.Posts;
import com.example.bloggingapp.model.Users;
import com.example.bloggingapp.repository.PostRepo;
import com.example.bloggingapp.repository.UsersRepo;
import com.example.bloggingapp.service.CommentService;
import com.example.bloggingapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller()
public class PostController {


    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/create/post")
    public String postFrom(Model model)  {
        Posts posts = new Posts();
        model.addAttribute("posts", posts);
        return "postForm";
    }

    @PostMapping("/create/post")
    public String createPost(Model model,
                             @ModelAttribute("users") Posts posts, Principal principal) {
        model.addAttribute("posts", posts);
       Users users =  usersRepo.findUsersByUserName(principal.getName());
        posts.setUser(users);
        posts.setCreationDate(new Date());
        Posts posts1 = postRepo.saveAndFlush(posts);
        System.out.println(posts1);
        return "redirect:/show/all";
    }

    @RequestMapping("/show/all")
    public String allApprovedPost(Model model) {

        Collection<Posts> posts =  postRepo.findAllApprovedPosts();
        model.addAttribute("posts", posts);

        return "postList";

    }

    @GetMapping("/approve/post")
    public String approvePost(Model model) {
        Collection<Posts> posts =  postRepo.findAllNonApprovedPosts();
        model.addAttribute("posts", posts);
        return "approvePostList";
    }
    @GetMapping("/my/post")
    public String myPost(Model model, Principal principal) {
        Users user = usersRepo.findUsersByUserName(principal.getName());
        Collection<Posts> posts =  postRepo.allPostByUserId(user.getId());
        model.addAttribute("posts", posts);
        return "myPost";
    }

    @RequestMapping("/all/post")
    public String allPost(Model model) {

        Collection<Posts> posts =  postRepo.findAllApprovedPosts();
        model.addAttribute("posts", posts);

        return "postList";

    }
    @GetMapping(value = "/post/approve/{id}")
    public String statusChange(@PathVariable Integer id, Model model)   {
        Posts post = postRepo.getById(id);
        if (post.isApproved() == false){
            post.setApproved(true);
        }
        postRepo.save(post);
        return "redirect:/approve/post";
    }

    @GetMapping(value = "/delete/post/{id}")
    public String deletePost(Principal principal, @PathVariable Integer id, Model model)   {
        postService.deletePost(id, principal);
        return "myPost";
    }
    @GetMapping("/view/post/{post_id}")
    public String viewPost(@PathVariable("post_id") Integer postId, Model model) {
        Posts posts = postService.getPostByPostId(postId);
        List<Comments> comments = commentService.coommentByPostId(postId);

        if (posts != null) {
            model.addAttribute("posts", posts);
            model.addAttribute("comments",new Comments());


            return "viewFullBlog";
        }
        return "redirect:/show/all";
    }

}
