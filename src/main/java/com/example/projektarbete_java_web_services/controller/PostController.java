package com.example.projektarbete_java_web_services.controller;

import com.example.projektarbete_java_web_services.dto.Post;
import com.example.projektarbete_java_web_services.services.PostService;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    public final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public List<Post> findAll() {
        return postService.findAll();
    }


    @PostMapping
    public Post insertPost(@RequestBody Post post) {
        return postService.insertPost(post);
    }


}

