package com.example.board.post.controller;

import com.example.board.post.domain.Post;
import com.example.board.post.dto.PostCreateReqDto;
import com.example.board.post.dto.PostListResDto;
import com.example.board.post.dto.PostUpdateReqDto;
import com.example.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;



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
    public String createPost(PostCreateReqDto dto, Model model) {
        try {
            Post post = service.create(dto);
            return "redirect:/post/detail/" + post.getId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "post/post-create";
        }
    }



//    Read
    @GetMapping("list/join")
    public String getAllPostsJoin(Model model) {
        model.addAttribute("posts", service.getAllPostsJoin());
        return "post/post-list";
    }

    @GetMapping("list/fetch")
    public String getAllPostsFetch(Model model) {
        model.addAttribute("posts", service.getAllPostsFetch());
        return "post/post-list";
    }

    @GetMapping("list/orderBy")
    public String getAllByOrderByCreatedTimeDesc(Model model) {
        model.addAttribute("posts", service.getAllByOrderBy());
        return "post/post-list";
    }

    @GetMapping("list/page")
    public String getAllPostsFetch(Model model,
                                   @PageableDefault(size=10, sort="createdTime",
                                   direction = Sort.Direction.DESC)
                                   Pageable pageable) {
        model.addAttribute("posts", service.getPostPageJason(pageable));
        return "post/post-page-list";
    }

    @GetMapping("list/notAppo")
    public String getAllPostNot(Model model,
                                   @PageableDefault(size=10, sort="createdTime",
                                           direction = Sort.Direction.DESC)
                                   Pageable pageable) {
        model.addAttribute("posts", service.getAllPostNot(pageable));
        return "post/post-page-list";
    }


    @GetMapping("list/page/jason")
    @ResponseBody
    public Page<PostListResDto> postPageJason(Pageable pageable) {
        Page<PostListResDto> dtos = service.getPostPageJason(pageable);
        return dtos;
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
