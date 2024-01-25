package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.AuthorDetailResDto;
import com.example.board.author.dto.AuthorUpdateReqDto;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorService Service;

    @MockBean
    private AuthorRepository mockRepo;

    @Test
    void findDetailByidTest(Long id){
        Long authorId = 1L;
        List<Post> posts = new ArrayList<>();
        Post post = Post.builder()
                .title("Testtitle")
                .content("Test content")
                .build();
        posts.add(post);

        Author author = Author.builder()
                .name("test1")
                .email("email4")
                .password("1324")
                .posts(posts)
                .build();

        Mockito.when(mockRepo.findById(authorId))
                .thenReturn(Optional.ofNullable(author));

        AuthorDetailResDto result = Service.findDetailById(authorId);
        assertEquals(author.getName(), result.getName());
        assertEquals(author.getEmail(), result.getEmail());
        assertEquals(author.getPassword(), result.getPassword());
        assertEquals(author.getPosts().size(), result.getPosts().size());
    }


    @Test
    void updateTest(){

        Long authorId = 1L;
        Author author = Author.builder()
                .name("test1")
                .email("email4")
                .password("1324")
                .build();

        Mockito.when(mockRepo.findById(authorId))
                .thenReturn(Optional.of(author));

        AuthorUpdateReqDto dto =
                new AuthorUpdateReqDto("updatedName","aksdjf");
//        Service.update(authorId)

        AuthorDetailResDto result = Service.update(authorId, dto);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getPassword(), result.getPassword());
    }



    @Test
    void findAllTest(){

        List<Author> authors = new ArrayList<>();
        Author author1 = new Author();
        Author author2 = new Author();

        author1.setId(43L);
        author2.setId(44L);

        authors.add(author1);
        authors.add(author2);

        Mockito.when(mockRepo.findAll())
                .thenReturn(authors);

//        검증
        assertEquals(authors.size(), Service.findAll().size());


    }
}
