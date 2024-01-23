package com.example.board.post.service;

import com.example.board.post.domain.Post;
import com.example.board.post.dto.PostListResDto;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository repository;
    public PostService(@Autowired PostRepository repository) {
        this.repository = repository;
    }


    public List<PostListResDto> getAllPosts() {

        List<PostListResDto> dtoList= new ArrayList<>();
        for (Post post : repository.findAll())
            dtoList.add(
                    new PostListResDto(
                        post.getId(),
                        post.getTitle()));
        return dtoList;
    }
}
