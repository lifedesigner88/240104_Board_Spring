package com.example.board.post.controller;

import com.example.board.post.domain.Post;
import com.example.board.post.dto.PostCreateReqDto;
import com.example.board.post.dto.PostDetailResDto;
import com.example.board.post.dto.PostListResDto;
import com.example.board.post.dto.PostUpdateReqDto;
import com.example.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    private final PostService service;
    public PostController(@Autowired PostService service) {
        this.service = service;
    }

//    Create
//    @GetMapping("create")


    @PostMapping("create")
    public Post createPost(PostCreateReqDto dto) {
        return service.create(dto);
    }

//    Read
    @GetMapping("list")
    public List<PostListResDto> getAllPosts() {
        return service.getAllPosts();
    }

    @GetMapping("detail/{id}")
    public PostDetailResDto getPostDetail(@PathVariable Long id) {
        return service.getPostDetail(id);
    }

//    Update
    @PostMapping("update/{id}")
    public Post updatePost(@PathVariable Long id, PostUpdateReqDto dto) {
        return service.update(id, dto);
    }

//    Delete
    @GetMapping("delete/{id}")
    public String deletePost(@PathVariable Long id) {
        service.deletePost(id);
        return "OK";
    }

}
