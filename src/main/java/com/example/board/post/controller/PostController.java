package com.example.board.post.controller;

import com.example.board.post.dto.PostListResDto;
import com.example.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    private final PostService service;

    public PostController(@Autowired PostService service) {
        this.service = service;
    }

    @GetMapping("/post/list")
    public List<PostListResDto> getAllPosts() {

        return service.getAllPosts();
    }
}
