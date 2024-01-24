package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.post.domain.Post;
import com.example.board.post.dto.PostCreateReqDto;
import com.example.board.post.dto.PostDetailResDto;
import com.example.board.post.dto.PostListResDto;
import com.example.board.post.dto.PostUpdateReqDto;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository repository;
    public PostService(@Autowired PostRepository repository) {
        this.repository = repository;
    }

//    Create
    public Post create(PostCreateReqDto dto) {
        Post post = Post.builder()
               .title(dto.getTitle())
               .content(dto.getContent())
               .build();
        return repository.save(post);
    }

//    Read
    public List<PostListResDto> getAllPosts() {
        List<PostListResDto> dtoList = new ArrayList<>();
        for (Post post : repository.findAll()){
            Author author = post.getAuthor();
            String email = author == null ? "익명" : author.getEmail();
            dtoList.add(
                    new PostListResDto(
                            post.getId(),
                            post.getTitle(),
                            email));
            }
        return dtoList;
    }

    public PostDetailResDto getPostDetail(Long id) {
       return makeResDto(findById(id));
    }


//    Update
    public Post update(Long id, PostUpdateReqDto dto) {
        Post post = findById(id);
        post.postUpdate(
                dto.getTitle(),
                dto.getContent());
        return repository.save(post);
    }

//    Delete
    public void deletePost(Long id) {repository.deleteById(id);}


    /* ECT */
    public Post findById(Long id){
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public PostDetailResDto makeResDto (Post p){
        return new PostDetailResDto(
                p.getId(),
                p.getTitle(),
                p.getContent(),
                p.getCreatedTime()
        );
    }
}
