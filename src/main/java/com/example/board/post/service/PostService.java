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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        Author author = findByEmail(dto.getEmail());
        String appointment = null;
        LocalDateTime dataTime = null;

        if(dto.getAppointment().equals("Y") && !dto.getAppointmentTime().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm");
            dataTime = LocalDateTime.parse(dto.getAppointmentTime(), formatter);
            LocalDateTime now = LocalDateTime.now();
            if (dataTime.isBefore(now))
                throw new IllegalArgumentException("Appointment time cannot be in the past");
            appointment = "Y";
        }


        Post post = Post.builder()
               .title(dto.getTitle())
               .content(dto.getContent())
               .author(author)
               .build();
        post.setAppointment(appointment,dataTime);
//        author.authorUpdate("DDDirty check","1234");

        return postRepo.save(post);
    }

//    Read
    public List<PostListResDto> getAllPostsJoin() {
        return getAllPostbyCustom(postRepo.findAllJoin());
    }
    public List<PostListResDto> getAllPostsFetch() {
        return getAllPostbyCustom(postRepo.findAllFetchJoin());
    }
    public List<PostListResDto> getAllByOrderBy() {
        return getAllPostbyCustom(postRepo.findAllByOrderByCreatedTimeDesc());
    }
    public PostDetailResDto getPostDetail(Long id) {
       return makeResDtoForDetail(findById(id));
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

//    Page
    public Page<PostListResDto> getPostPageJason(Pageable pageable){
        return postRepo
                .findAll(pageable)
                .map(this::makeResDtoForList);
    }
    public Page<PostListResDto> getAllPostNot(Pageable pageable) {
        return postRepo
                .findByAppointment(null, pageable)
                .map(this::makeResDtoForList);

    }

//    Etc
    public Post findById(Long id) throws EntityNotFoundException {
        return postRepo.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
    public Author findByEmail(String email) {
        return authorRepo.findByEmail(email)
                .orElse(null);
    }
    public PostListResDto makeResDtoForList (Post p){
        Author author = p.getAuthor();
        String authorEmail = author == null ? "[익명]" : author.getEmail();
        return new PostListResDto(
                p.getId(),
                p.getTitle(),
                authorEmail
        );
    }
    public PostDetailResDto makeResDtoForDetail (Post p){
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
    public List<PostListResDto> getAllPostbyCustom (List<Post> list){
        List<PostListResDto> dtoList = new ArrayList<>();
        for (Post post : list) dtoList.add(makeResDtoForList(post));
        return dtoList;
    }

}
