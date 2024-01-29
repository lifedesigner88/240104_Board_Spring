package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import com.example.board.author.dto.AuthorDetailResDto;
import com.example.board.author.dto.AuthorListResDto;
import com.example.board.author.dto.AuthorSaveReqDto;
import com.example.board.author.dto.AuthorUpdateReqDto;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dto.PostListResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository repository;
    public AuthorService(@Autowired AuthorRepository repository) {
        this.repository = repository;
    }

//    Create
    public Author save(AuthorSaveReqDto req) throws IllegalArgumentException {
        Role role;
        String reqRole = req.getRole();
        if (reqRole == null || reqRole.equals("admin")) role = Role.ADMIN;
        else role = Role.USER;

        Optional<Author> optionalAuthor = repository.findByEmail(req.getEmail());
        if (optionalAuthor.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

//        cascade.persist 테스트
//        부모테이블을 통해 자식테이블에 객체를 동시에 생성.

        Author author = Author.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(req.getPassword())
                .role(role)
                .build();

//        Post post = Post.builder()
//                .title("안녕하세요" + author.getName() + "입니다")
//                .content("qksrkqtmqslek. cascade 테스트 입니다.")
//                .author(author)
//                .build();
//
//        List<Post> posts = new ArrayList<>();z
//        posts.add(post);
//        author.setPosts(posts);

        return repository.save(author);

    }

//    Read
    public List<AuthorListResDto> findAll(){
        List<AuthorListResDto> dtoList = new ArrayList<>();
        for(Author author : repository.findAll())
            dtoList.add(
                    new AuthorListResDto(
                            author.getId(),
                            author.getName(),
                            author.getEmail(),
                            author.getPosts().size()
                    ));
        return dtoList;
    }

    public AuthorDetailResDto findDetailById(Long id) throws EntityNotFoundException {
        return makeResDto(findById(id));
    }

//    Update
    @Transactional
    public AuthorDetailResDto update (Long id, AuthorUpdateReqDto dto){
        Author author = findById(id);
        author.authorUpdate(
                dto.getName(),
                dto.getPassword());
        return makeResDto(author);
//        객체에 변경이 감지(dirtychecking)되면 트랜잭션이 완료되는 시점에 save 동작
//        트렌젝셔널 입력해야 한다. (메서드 종료 = 트렌잭션 완료)
    }

//    Delete
    public void delete(Long id){repository.deleteById(id);}


    /* ECT */
    public Author findById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public AuthorDetailResDto makeResDto (Author a){
        Role roleRes = a.getRole();
        String roleString;
        if (roleRes == null || roleRes.equals(Role.ADMIN)) roleString = "관리자";
        else roleString = "유저";

        List<PostListResDto> dtoList = new ArrayList<>();
        for(Post p : a.getPosts())
            dtoList.add(
                    new PostListResDto(
                            p.getId(),
                            p.getTitle(),
                            null
                    ));

        return new AuthorDetailResDto(
                a.getId(),
                a.getName(),
                a.getEmail(),
                a.getPassword(),
                a.getCreatedTime(),
                a.getPosts().size(),
                dtoList,
                roleString
        );
    }


}
