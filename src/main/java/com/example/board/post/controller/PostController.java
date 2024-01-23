package com.example.board.post.controller;

import com.example.board.post.domain.Post;
import com.example.board.post.dto.PostCreateReqDto;
import com.example.board.post.dto.PostUpdateReqDto;
import com.example.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("post")
public class PostController {

    private final PostService service;
    public PostController(@Autowired PostService service) {
        this.service = service;
    }

//    Create
    @GetMapping("create")
    public String postCreateForm(){
        return "post/post-create";
    }

    @PostMapping("create")
    public String createPost(PostCreateReqDto dto) {
        Post post = service.create(dto);
        return "redirect:/post/detail/" + post.getId();
    }

//    Read
    @GetMapping("list")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", service.getAllPosts());
        return "post/post-list";
    }

    @GetMapping("detail/{id}")
    public String getPostDetail(@PathVariable Long id, Model model) {
        model.addAttribute("post", service.getPostDetail(id));
        return "post/post-detail";
    }

//    Update
    @PostMapping("update/{id}")
    public String updatePost(@PathVariable Long id, PostUpdateReqDto dto) {
        Post post =  service.update(id, dto);
        return "redirect:/post/detail/" + post.getId();

    }

//    Delete
    @GetMapping("delete/{id}")
    public String deletePost(@PathVariable Long id) {
        service.deletePost(id);
        return "redirect:/post/list";
    }

}
