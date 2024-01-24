package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dto.PostCreateReqDto;
import com.example.board.post.dto.PostDetailResDto;
import com.example.board.post.dto.PostListResDto;
import com.example.board.post.dto.PostUpdateReqDto;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepo;
    private final AuthorRepository authorRepo;

    @Autowired
    public PostService(PostRepository postRepo, AuthorRepository authorRepo) {
        this.postRepo = postRepo;
        this.authorRepo = authorRepo;
    }

//    Create
    public Post create(PostCreateReqDto dto) {

        Post post = Post.builder()
               .title(dto.getTitle())
               .content(dto.getContent())
               .author(findByEmail(dto.getEmail()))
               .build();
        return postRepo.save(post);
    }

//    Read
    public List<PostListResDto> getAllPosts() {
        List<PostListResDto> dtoList = new ArrayList<>();
        for (Post post : postRepo.findAll()) {
            Author author = post.getAuthor();
            String authorEmail = author == null ? "[익명]" : author.getEmail();
            dtoList.add(
                    new PostListResDto(
                            post.getId(),
                            post.getTitle(),
                            authorEmail));}
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
        return postRepo.save(post);
    }

//    Delete
    public void deletePost(Long id) {postRepo.deleteById(id);}


    /* ECT */
    public Post findById(Long id) throws EntityNotFoundException {
        return postRepo.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
    public Author findByEmail(String email) {
        return authorRepo.findByEmail(email)
                .orElse(null);
    }

    public PostDetailResDto makeResDto (Post p){
        Author author = p.getAuthor();
        String authorEmail = author == null ? "[익명]" : author.getEmail();
        String authorName = author == null ? "[익명]" : author.getName();
        return new PostDetailResDto(
                p.getId(),
                p.getTitle(),
                p.getContent(),
                p.getCreatedTime(),
                authorEmail,
                authorName
        );
    }
}
